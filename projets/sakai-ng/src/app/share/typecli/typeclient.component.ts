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

import { TypeClient } from './typeclient.model';
import { TypeClientService } from './typeclient.service';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DropdownModule } from 'primeng/dropdown';
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
import { KeycloakService } from '../../config/keycloak-config/keycloak.service';
import { LogService } from '../log/log.service';
import { EntityName, LogType } from '../log/app-logs.model';
import { debounceTime, distinctUntilChanged, Subject, switchMap } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'typeclient',
  templateUrl: 'typeclient.component.html',
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
        IconFieldModule,
        CommonModule
  ],
  providers: [
    ConfirmationService,
    MessageService,
    TypeClientService
  ]
})
export class TypeClientComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly confirmationService = inject(ConfirmationService);
  private readonly messageService = inject(MessageService);
  readonly typeClientService = inject(TypeClientService);
  private readonly logService = inject(LogService);
  private readonly keycloakService = inject(KeycloakService);

  typeClientList = signal<TypeClient[]>([]);
  formVisible = signal<boolean>(false);
  isWorking = signal<boolean>(false);

  username: string = '';

rechercheLibelle = '';
typeClientTrouve?: TypeClient;
  private searchSubject = new Subject<string>();

  typeClientForm: FormGroup;

  constructor() {
    this.typeClientForm = this.fb.group({
      code: [''],
      libelle: ['', Validators.required],
    
    });

    this.init();
  }

  
  private initSearchListener() {
  this.searchSubject.pipe(
    debounceTime(400),
    distinctUntilChanged(),
    switchMap(libelle =>
      libelle ? this.typeClientService.getTypeClientByLibelle(libelle) : []
    )
  ).subscribe({
    next: (typeClient) => this.typeClientTrouve = typeClient,
    error: () => this.typeClientTrouve = undefined // pas trouvé ou erreur
  });
}

onSearchChange(value: string): void {
  this.searchSubject.next(value.trim());
}

  async init() {
    this.typeClientList.set(await this.typeClientService.getAllTypeClients());
     const user = this.keycloakService.getInstance().tokenParsed;
        const firstName = user?.['given_name'] || '';
        const lastName = user?.['family_name'] || '';
        this.username = `${firstName} ${lastName}`.trim() || 'Utilisateur';
  }

  closeForm() {
    this.typeClientForm.reset();
    this.formVisible.set(false);
  }

  async submitTypeClientForm() {
    this.isWorking.set(true);
    try {
      const username = this.keycloakService.getInstance().tokenParsed?.['preferred_username'] || '';
      const formData = this.typeClientForm.value;
      console.log(formData);
      if (formData.code) {
        await this.typeClientService.updateTypeClient(formData);
        this.typeClientList.update(val => {
          const index = val.findIndex(t => t.code === formData.code);
          if (index !== -1) val[index] = formData;
          return val;
        });
         this.logService.createLog(
                LogType.UPDATE,
                EntityName.TYPE_CLIENT,
                username,
                formData.libelle,
                "Modification d'un type client"
              ).subscribe();
      }
      else {
        const newTypeClient = await this.typeClientService.addTypeClient(formData);
        this.typeClientList.update(val => {
          val.push(newTypeClient);
          return val;
        });
         this.logService.createLog(
                LogType.CREATE,
                EntityName.TYPE_CLIENT,
                username,
                newTypeClient.libelle!,
                "Création d'un type client"
              ).subscribe();
      }

      this.closeForm();
      this.messageService.add({ severity: 'info', summary: 'Réussie', detail: 'Opération terminée', life: 3000 });

    } catch (error) {
      this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Erreur durant l\'opération', life: 3000 });
    } finally {
      this.isWorking.set(false);
    }
  }

  updateTypeClient(typeClient: TypeClient) {
    this.typeClientForm.patchValue(typeClient);
    this.formVisible.set(true);
  }

  deleteTypeClient(event: Event, typeClient: TypeClient) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Voulez-vous vraiment supprimer ce type de client ?',
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
          await this.typeClientService.deleteTypeClient(typeClient.code);
          this.typeClientList.update(val => val.filter(t => t.code !== typeClient.code));
          this.messageService.add({ severity: 'info', summary: 'Succès', detail: 'Suppression réussie', life: 3000 });
          this.logService.createLog(
          LogType.DELETE,
          EntityName.TYPE_CLIENT,
          username,
          typeClient.libelle!,
          "Suppression d'un type client"
        ).subscribe();
        } catch (error) {
          this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Échec de la suppression', life: 3000 });
        }
      },
      reject: () => {}
    });
  }

  ngOnInit(): void {
     this.initSearchListener();
  this.init(); // ta méthode d'initialisation déjà existante
  }
}
