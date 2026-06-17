import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { ToastModule } from 'primeng/toast';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { DropdownModule } from 'primeng/dropdown';
import { ConfirmationService, MessageService } from 'primeng/api';

import { TarifService } from './tarif.service';
import { Tarif } from './tarif.model';
import { NewTarifRequest } from './newtarif.model';
import { SelectModule } from 'primeng/select';
import { TypeClient } from '../typecli/typeclient.model';
import { TypeClientService } from '../typecli/typeclient.service';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputNumberModule } from 'primeng/inputnumber';
import { RadioButtonModule } from 'primeng/radiobutton';
import { RatingModule } from 'primeng/rating';
import { RippleModule } from 'primeng/ripple';
import { TagModule } from 'primeng/tag';
import { TextareaModule } from 'primeng/textarea';
import { ToolbarModule } from 'primeng/toolbar';
import { TypeBranchService } from '../typebranch/typebranch.service';
import { TypeBranch } from '../typebranch/typebranch.model';
import { KeycloakService } from '../../config/keycloak-config/keycloak.service';
import { LogService } from '../log/log.service';
import { EntityName, LogType } from '../log/app-logs.model';

@Component({
  selector: 'tarif',
  templateUrl: 'tarif.component.html',
  standalone: true,
  imports: [
    TableModule,
    ButtonModule,
    DialogModule,
    InputTextModule,
    FormsModule,
    ReactiveFormsModule,
    ToastModule,
    ConfirmPopupModule,
    SelectModule,
    DropdownModule,
    ProgressSpinnerModule,
    ConfirmDialogModule,
    ConfirmPopupModule,
    DropdownModule,
    ProgressSpinnerModule,
    RippleModule,
    ToolbarModule,
    RatingModule,
    TextareaModule,
    SelectModule,
    RadioButtonModule,
    InputNumberModule,
    TagModule,
    InputIconModule,
    IconFieldModule
  ],
  providers: [ConfirmationService, MessageService, TarifService, TypeClientService]
})
export class TarifComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly confirmationService = inject(ConfirmationService);
  private readonly messageService = inject(MessageService);
  readonly tarifService = inject(TarifService);
  readonly typeBranchementService = inject(TypeBranchService);
  readonly typeClientService = inject(TypeClientService);
  private readonly logService = inject(LogService);
      private readonly keycloakService = inject(KeycloakService);

  tarifList = signal<Tarif[]>([]);
  typeBranchementList = signal<TypeBranch[]>([]);
  typeClientList = signal<TypeClient[]>([]);
  formVisible = signal<boolean>(false);
  isWorking = signal<boolean>(false);

  username: string = '';

  searchLibelle: string = '';
  searchTimeout: any;
  tarifTrouve?: Tarif;


  tarifForm: FormGroup;

  constructor() {
    this.tarifForm = this.fb.group({
      code: [''],
      libelle: ['', Validators.required],
      reglDisjonct: [0, Validators.required],
      puissance: [0, Validators.required],
      tarifHp: [0, Validators.required],
      tarifHpt: [0, Validators.required],
      tarifHc: [0, Validators.required],
      loccpt: [0, Validators.required],
      locposte: [0, Validators.required],
      loctransf: [0, Validators.required],
      mntAvCons: [0, Validators.required],
      fraisPol: [0, Validators.required],
      fraisTimb: [0, Validators.required],
      mntPrimFix: [0, Validators.required],
      mntRedev: [0, Validators.required],
      typeClientCode: ['', Validators.required],
      typeBranch_Code: ['', Validators.required]
    });

    this.init();
  }

  async init() {
    this.tarifList.set(await this.tarifService.getAllTarifs());
    this.typeBranchementList.set(await this.typeBranchementService.getAllTypeBranch());
    this.typeClientList.set(await this.typeClientService.getAllTypeClients());
     const user = this.keycloakService.getInstance().tokenParsed;
        const firstName = user?.['given_name'] || '';
        const lastName = user?.['family_name'] || '';
        this.username = `${firstName} ${lastName}`.trim() || 'Utilisateur';
  }

  closeForm() {
    this.tarifForm.reset({
      code: '',
      libelle: '',
      reglDisjonct: 0,
      puissance: 0,
      tarifHp: 0,
      tarifHpt: 0,
      tarifHc: 0,
      loccpt: 0,
      locposte: 0,
      loctransf: 0,
      mntAvCons: 0,
      fraisPol: 0,
      fraisTimb: 0,
      mntPrimFix: 0,
      mntRedev: 0,
      typeBranch_Code: undefined,
      typeClientCode: undefined
    });
    this.formVisible.set(false);
  }


  rechercherParLibelle(): void {
  if (this.searchTimeout) {
    clearTimeout(this.searchTimeout);
  }

  // Attente de 1 seconde d'inactivité avant la recherche
  this.searchTimeout = setTimeout(() => {
    const libelle = this.searchLibelle.trim();
    if (libelle !== '') {
      this.tarifService.getTarifByLibelle(libelle).subscribe({
        next: (tarif) => {
          this.tarifTrouve = tarif;
        },
        error: () => {
          this.tarifTrouve = undefined;
          this.messageService.add({
            severity: 'warn',
            summary: 'Aucun tarif trouvé',
            life: 3000
          });
        }
      });
    } else {
      this.tarifTrouve = undefined;
    }
  }, 1000); // 1 seconde (1000 ms)
}

  async submitTarifForm() {
    this.isWorking.set(true);

    try {
        const username = this.keycloakService.getInstance().tokenParsed?.['preferred_username'] || '';

      const formData = this.tarifForm.value;
      const isUpdate = !!formData.code;

      if (!isUpdate) {
        const request: NewTarifRequest = {
          libelle: formData.libelle,
          reglDisjonct: formData.reglDisjonct,
          puissance: formData.puissance,
          tarifHp: formData.tarifHp,
          tarifHpt: formData.tarifHpt,
          tarifHc: formData.tarifHc,
          loccpt: formData.loccpt,
          locposte: formData.locposte,
          loctransf: formData.loctransf,
          mntAvCons: formData.mntAvCons,
          fraisPol: formData.fraisPol,
          fraisTimb: formData.fraisTimb,
          mntPrimFix: formData.mntPrimFix,
          mntRedev: formData.mntRedev,
          typeBranchCode: formData.typeBranch_Code,
          typeClientCode: formData.typeClientCode
        };

        const newTarif = await this.tarifService.addTarif(request);
        this.tarifList.update(list => [...list, newTarif]);
          this.logService.createLog(
                        LogType.CREATE,
                        EntityName.TARIF,
                        username,
                        newTarif.libelle,
                        "Création d'un tarif"
                      ).subscribe();
      }
      
      else {
        const updatedTarif: Tarif = {
          ...(formData as Tarif)
        };
        await this.tarifService.updateTarif(updatedTarif);
        this.tarifList.update(list =>
          list.map(t => (t.code === formData.code ? { ...t, ...formData } : t))
        );
         this.logService.createLog(
                LogType.UPDATE,
                EntityName.TARIF,
                username,
                updatedTarif.libelle,
                "Modification d'un tarif"
              ).subscribe();
      }

      this.closeForm();
      this.messageService.add({ severity: 'info', summary: 'Réussie', detail: 'Opération terminée', life: 3000 });

    } catch (error) {
      console.error('Erreur :', error);
      this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Erreur durant l\'opération', life: 3000 });
    } finally {
      this.isWorking.set(false);
    }
  }

  updateTarif(tarif: Tarif) {
    this.tarifForm.patchValue(tarif);
    this.formVisible.set(true);
  }

  deleteTarif(event: Event, tarif: Tarif) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Voulez-vous vraiment supprimer ce tarif ?',
      icon: 'pi pi-exclamation-triangle',
      rejectButtonProps: {
        label: 'NON',
        severity: 'secondary',
        outlined: true
      },
      acceptButtonProps: {
        label: 'OUI'
      },
      accept: async () => {
        try {
          const username = this.keycloakService.getInstance().tokenParsed?.['preferred_username'] || '';

          await this.tarifService.deleteTarif(tarif.code);
          this.tarifList.update(list => list.filter(t => t.code !== tarif.code));
          this.messageService.add({ severity: 'info', summary: 'Succès', detail: 'Suppression réussie', life: 3000 });

          this.logService.createLog(
            LogType.DELETE,
            EntityName.TARIF,
            username,
            tarif.libelle,
            'Suppression d\'un tarif'
            ).subscribe();

        } catch (error) {
          this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Échec de la suppression', life: 3000 });
        }
      },
      reject: () => {}
    });
  }

  ngOnInit() {}
}
