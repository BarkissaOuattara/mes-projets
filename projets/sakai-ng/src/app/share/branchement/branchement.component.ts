import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { ToastModule } from 'primeng/toast';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DropdownModule } from 'primeng/dropdown';

import { Branchement } from './branchement.model';
import { BranchementService } from './branchement.service';
import { Exploitation } from '../exploitation/exploitation.model';
import { ExploitationService } from '../exploitation/exploitation.service';
import { NewBranchementRequest } from './newbranchement.model';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputNumberModule } from 'primeng/inputnumber';
import { RadioButtonModule } from 'primeng/radiobutton';
import { RatingModule } from 'primeng/rating';
import { RippleModule } from 'primeng/ripple';
import { SelectModule } from 'primeng/select';
import { TagModule } from 'primeng/tag';
import { TextareaModule } from 'primeng/textarea';
import { ToolbarModule } from 'primeng/toolbar';
import { TypeBranchService } from '../typebranch/typebranch.service';
import { TypeBranch } from '../typebranch/typebranch.model';
import { LogService } from '../log/log.service';
import { KeycloakService } from '../../config/keycloak-config/keycloak.service';
import { EntityName, LogType } from '../log/app-logs.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'branchement',
  templateUrl: 'branchement.component.html',
  standalone: true,
  imports: [
    TableModule,
        ButtonModule,
        DialogModule,
        InputTextModule,
        FormsModule,
        ReactiveFormsModule,
        ToastModule,
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
        IconFieldModule,
        CommonModule
  ],
  providers: [
    ConfirmationService,
    MessageService,
    BranchementService
  ]
})
export class BranchementComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly confirmationService = inject(ConfirmationService);
  private readonly messageService = inject(MessageService);
  readonly branchementService = inject(BranchementService);
  readonly typeBranchementService = inject(TypeBranchService);
  readonly exploitationService = inject(ExploitationService);
  private readonly logService = inject(LogService);
    private readonly keycloakService = inject(KeycloakService);

  branchementList = signal<Branchement[]>([]);
  typeBranchementList = signal<TypeBranch[]>([]);
  exploitationList = signal<Exploitation[]>([]);
  formVisible = signal<boolean>(false);
  isWorking = signal<boolean>(false);

  username: string = '';
  branchementForm: FormGroup;

  etatOptions!: { label: string; value: string }[];
  searchForm!: FormGroup;
  resultatsBranchement: Branchement[] = [];

  constructor() {
    this.branchementForm = this.fb.group({
      code: [undefined],
      explCode: [undefined, Validators.required],
      section: ['', Validators.required],
      lot: [0, Validators.required],
      parcelle: [0, Validators.required],
      rang: [0, Validators.required],
      etat: ['', Validators.required],
      typeBranch_Code: ['', Validators.required],
      avoirs: [0],
     nom: ['', [Validators.required, this.noNumbersValidator]],
prenom: ['', [Validators.required, this.noNumbersValidator]],

      tel: [''],
      rue: ['']
    });
    this.init();
  }

  private noNumbersValidator(control: import('@angular/forms').AbstractControl): { [key: string]: any } | null {
  return /^[^\d]*$/.test(control.value) ? null : { numbersNotAllowed: true };
}


  async init() {
    this.branchementList.set(await this.branchementService.getAllBranchements());
    this.typeBranchementList.set(await this.typeBranchementService.getAllTypeBranch());
    this.exploitationList.set(await this.exploitationService.getAllExploitation());
     const user = this.keycloakService.getInstance().tokenParsed;
        const firstName = user?.['given_name'] || '';
        const lastName = user?.['family_name'] || '';
        this.username = `${firstName} ${lastName}`.trim() || 'Utilisateur';
  }

  closeForm() {
    this.branchementForm.reset({
      code: undefined,
      explCode: undefined,
      section: '',
      lot: undefined,
      parcelle: undefined,
      rang: undefined,
      etat: '',
      typeBranch_Code: '',
      avoirs: 0,
      nom: '',
      prenom: '',
      tel: '',
      rue: ''
    });
    this.formVisible.set(false);
  }

resetSearchForm() {
  this.searchForm.reset();
  this.resultatsBranchement = []; // vide les résultats filtrés
  this.loadAllBranchements();     // recharge toute la liste
}

async loadAllBranchements() {
  try {
    const data = await this.branchementService.getAllBranchements();
    this.branchementList.set(data);
  } catch (error) {
    console.error("Erreur lors du chargement des branchements :", error);
  }
}

  async searchBranchement() {
    if (this.searchForm.valid) {
      const { lot, parcelle, rang, section, codeExpl } = this.searchForm.value;
      try {
        this.resultatsBranchement = await this.branchementService.searchBranchement(
          lot,
          parcelle,
          rang,
          section,
          codeExpl
        );
      } catch (err) {
        console.error('Erreur lors de la recherche :', err);
      }
    }
  }
  async submitBranchementForm() {
    this.isWorking.set(true);

    try {
          const username = this.keycloakService.getInstance().tokenParsed?.['preferred_username'] || '';

      const formData = { ...this.branchementForm.value };

      const isUpdate = !!formData.code;

      if (!isUpdate) {
        delete formData.code;
      }

      if (isUpdate) {
        await this.branchementService.updateBranchement(formData);
        this.branchementList.update(val => {
          const index = val.findIndex(b => b.code === formData.code);
          if (index !== -1) {
            val[index] = { ...val[index], ...formData };
          }
          return val;
        });
        this.logService.createLog(
        LogType.UPDATE,
        EntityName.BRANCHEMENT,
        username,
        formData.tel,
        "Modification d'un branchement"
      ).subscribe();
      } else {
        const request: NewBranchementRequest = {
          section: formData.section,
          lot: formData.lot,
          parcelle: formData.parcelle,
          rang: formData.rang,
          etat: formData.etat,
          avoirs: formData.avoirs,
          nom: formData.nom,
          prenom: formData.prenom,
          tel: formData.tel,
          rue: formData.rue,
          typeBranchCode: formData.typeBranch_Code,
          exploitation: formData.explCode,
        };
        const newB = await this.branchementService.addBranchement(request);
        this.branchementList.update(val => {
          val.push(newB);
          return val;
        });
        this.logService.createLog(
        LogType.CREATE,
        EntityName.BRANCHEMENT,
        username,
        newB.tel!,
        "Création d'un branchement"
      ).subscribe();
      }

      this.closeForm();
      this.messageService.add({ severity: 'info', summary: 'Réussie', detail: 'Opération terminée', life: 3000 });

    } catch (error) {
      console.error("Erreur :", error);
      this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Erreur durant l\'opération', life: 3000 });
    } finally {
      this.isWorking.set(false);
    }
  }

 getColor(etat: string): string {
  return etat?.toUpperCase() === 'ACTIF'
    ? 'text-green-600 font-bold'
    : 'text-red-600 font-bold';
}

  updateBranchement(branchement: Branchement) {
    this.branchementForm.patchValue(branchement);
    this.formVisible.set(true);
  }

  deleteBranchement(event: Event, branchement: Branchement) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Voulez-vous vraiment supprimer ce branchement ?',
      icon: 'pi pi-exclamation-triangle',
      rejectButtonProps: {
        label: 'NON',
        severity: 'secondary',
        outlined: true
      },
      acceptButtonProps: {
        label: 'Oui'
      },
      accept: async () => {
        try {
          const username = this.keycloakService.getInstance().tokenParsed?.['preferred_username'] || '';
          await this.branchementService.deleteBranchement(branchement.code);
          this.branchementList.update(val => val.filter(b => b.code !== branchement.code));
          this.logService.createLog(
          LogType.DELETE,
          EntityName.BRANCHEMENT,
          username,
          branchement.tel!,
          "Suppression d'un branchement"
        ).subscribe();
          this.messageService.add({ severity: 'info', summary: 'Succès', detail: 'Suppression réussie', life: 3000 });
        } catch (error) {
          this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Échec de la suppression', life: 3000 });
        }
      },
      
      reject: () => {}
    });
  }

ngOnInit() {
  this.searchForm = this.fb.group({
    lot: [null, Validators.required],
    parcelle: [null, Validators.required],
    rang: [null, Validators.required],
    section: ['', Validators.required],
    codeExpl: [null, Validators.required]
    
  });

  this.etatOptions = [
  { label: 'Actif', value: 'ACTIF' },
  { label: 'Inactif', value: 'INACTIF' }
];

}
  
}
