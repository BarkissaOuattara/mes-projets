import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { TableModule } from 'primeng/table';
import { ToolbarModule } from 'primeng/toolbar';
import { TypeBranchService } from './typebranch.service';
import type { TypeBranch } from './typebranch.model';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { RadioButtonModule } from 'primeng/radiobutton';
import { RatingModule } from 'primeng/rating';
import { RippleModule } from 'primeng/ripple';
import { SelectModule } from 'primeng/select';
import { TagModule } from 'primeng/tag';
import { TextareaModule } from 'primeng/textarea';
import { ToastModule } from 'primeng/toast';
import { KeycloakService } from '../../config/keycloak-config/keycloak.service';
import { LogService } from '../log/log.service';
import { LogType, EntityName } from '../log/app-logs.model';

@Component({
  selector: 'app-typebranch',
  standalone: true,
  templateUrl: './typebranch.component.html',
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
    IconFieldModule
  ],
  providers: [MessageService, ConfirmationService]
})
export class TypeBranchComponent implements OnInit {
  typeBranchForm!: FormGroup;
  formVisible = new BehaviorSubject<boolean>(false);
  private readonly logService = inject(LogService);
  private readonly keycloakService = inject(KeycloakService);
  isEditMode = false;
  loading = false;

  username: string = '';

  codeRecherche: string = '';
searchTimeout: any;
typeBranchementTrouve?: TypeBranch;

  typeBranchList: TypeBranch[] = [];

  constructor(
    private fb: FormBuilder,
    private typeBranchService: TypeBranchService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadTypeBranches();
     const user = this.keycloakService.getInstance().tokenParsed;
        const firstName = user?.['given_name'] || '';
        const lastName = user?.['family_name'] || '';
        this.username = `${firstName} ${lastName}`.trim() || 'Utilisateur';
  }

  initForm(): void {
    this.typeBranchForm = this.fb.group({
      code: ['', Validators.required],
      calibrage: ['', Validators.required],
      libelle: ['', Validators.required],
      fraisrcvrt: [0, [Validators.required, Validators.min(0)]],
      fraisremise: [0, [Validators.required, Validators.min(0)]],
      fraisrepose: [0, [Validators.required, Validators.min(0)]],
      fraisetal: [0, [Validators.required, Validators.min(0)]],
      forfTypebr: [0, [Validators.required, Validators.min(0)]],
      forfMntBran: [0, [Validators.required, Validators.min(0)]],
      forfGle: [0, [Validators.required, Validators.min(0)]],
    });
  }

onSearchCodeChange(): void {
  if (this.searchTimeout) {
    clearTimeout(this.searchTimeout);
  }

  this.searchTimeout = setTimeout(() => {
    const code = this.codeRecherche.trim();

    if (code) {
      this.typeBranchService.getTypeBranchByCode(code).subscribe({
        next: (data) => {
          this.typeBranchementTrouve = data;
        },
        error: () => {
          this.typeBranchementTrouve = undefined;
          // Afficher un message si on veut, ou rien pour éviter toute alerte
          this.messageService.add({
            severity: 'warn',
            summary: 'Aucun type de branchement trouvé',
            life: 3000,
          });
        }
      });
    } else {
      this.typeBranchementTrouve = undefined;
    }
  }, 500); // 500ms d'inactivité avant la recherche
}


  loadTypeBranches(): void {
    this.loading = true;
    this.typeBranchService.getAllTypeBranch()
      .then(data => {
        this.typeBranchList = data;
        this.loading = false;
      })
      .catch(error => {
        console.error(error);
        this.loading = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Erreur',
          detail: 'Échec de chargement des types de branchement.'
        });
      });
  }

  submitTypeBranchForm(): void {
    if (this.typeBranchForm.invalid) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Validation',
        detail: 'Veuillez remplir correctement le formulaire.'
      });
      return;
    }

    const username = this.keycloakService.getInstance().tokenParsed?.['preferred_username'] || '';

    const formValue = this.typeBranchForm.value;
    const payload: TypeBranch = { ...formValue };
    

    if (this.isEditMode) {
      this.typeBranchService.updateTypeBranch(formValue.code, payload).subscribe({
        next: () => {
           this.logService.createLog(
                          LogType.UPDATE,
                          EntityName.TYPE_BRANCHEMENT,
                          username,
                          formValue.code,
                          "Modification d'un tarif"
                        ).subscribe();
          this.messageService.add({ severity: 'success', summary: 'Mis à jour', detail: 'Type de branchement mis à jour' });
          this.loadTypeBranches();
          this.closeForm();
        },
        error: () => {
          this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Échec de la mise à jour' });
        }
      });
    } else {
      this.typeBranchService.createTypeBranch(payload).subscribe({
        next: () => {
          this.logService.createLog(
                  LogType.CREATE,
                  EntityName.TYPE_BRANCHEMENT,
                  username,
                  payload.code,
                  "Création d'une exploitation"
                ).subscribe();
          this.messageService.add({ severity: 'success', summary: 'Ajouté', detail: 'Type de branchement ajouté' });
          this.loadTypeBranches();
          this.closeForm();
        },
        error: () => {
          this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Échec de l’ajout' });
        }
      });
    }
  }

  updateTypeBranch(typeBranch: TypeBranch): void {
    this.typeBranchForm.patchValue({ ...typeBranch });
    this.isEditMode = true;
    this.formVisible.next(true);
  }

  deleteTypeBranch(event: Event, typeBranch: TypeBranch): void {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: `Voulez-vous vraiment supprimer le type ${typeBranch.code} ?`,
      icon: 'pi pi-exclamation-triangle',
      acceptButtonProps: { label: 'Oui' },
      rejectButtonProps: { label: 'Non', severity: 'secondary', outlined: true },
      accept: () => {
        const username = this.keycloakService.getInstance().tokenParsed?.['preferred_username'] || '';
        this.typeBranchService.deleteTypeBranch(typeBranch.code).subscribe({
          next: () => {
             this.logService.createLog(
            LogType.DELETE,
            EntityName.TYPE_BRANCHEMENT,
            username,
            typeBranch.code,
            "Suppression d'une exploitation"
          ).subscribe();
            this.typeBranchList = this.typeBranchList.filter(t => t.code !== typeBranch.code);
            this.messageService.add({ severity: 'info', summary: 'Succès', detail: 'Type de branchement supprimé', life: 3000 });
          },
          error: () => {
            this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Échec de la suppression', life: 3000 });
          }
        });
      }
    });
  }

  closeForm(): void {
    this.typeBranchForm.reset();
    this.isEditMode = false;
    this.formVisible.next(false);
  }
}
