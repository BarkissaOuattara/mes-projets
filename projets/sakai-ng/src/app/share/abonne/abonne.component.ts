import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule, AbstractControl, ValidationErrors } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { ToastModule } from 'primeng/toast';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { DropdownModule } from 'primeng/dropdown';
import { ConfirmationService, MessageService } from 'primeng/api';

import { AbonneService } from './abonne.service';
import { Abonne, AbonneDTO } from './abonne.model';
import { NewAbonneRequest } from './newAbonne.model';
import { TypeClientService } from '../typecli/typeclient.service';
import { ExploitationService } from '../exploitation/exploitation.service';
import { TypeClient } from '../typecli/typeclient.model';
import { Exploitation } from '../exploitation/exploitation.model';
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
import { CommonModule, DatePipe } from '@angular/common';
import { TabsModule } from 'primeng/tabs';
import { EntityName, LogType } from '../log/app-logs.model';
import { LogService } from '../log/log.service';
import { KeycloakService } from '../../config/keycloak-config/keycloak.service';
import { filter } from 'rxjs';

@Component({
    selector: 'abonne',
    templateUrl: 'abonne.component.html',
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
        TabsModule,
        CommonModule,
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
    providers: [ConfirmationService, MessageService, AbonneService, TypeClientService, ExploitationService, DatePipe]
})
export class AbonneComponent implements OnInit {
    private readonly fb = inject(FormBuilder);
    private readonly messageService = inject(MessageService);
    private readonly confirmationService = inject(ConfirmationService);
    private readonly abonneService = inject(AbonneService);
    private readonly typeClientService = inject(TypeClientService);
    private readonly exploitationService = inject(ExploitationService);
    private readonly logService = inject(LogService);
    private readonly keycloakService = inject(KeycloakService);

    abonneList = signal<Abonne[]>([]);
    typeClientList = signal<TypeClient[]>([]);
    exploitationList = signal<Exploitation[]>([]);
    formVisible = signal(false);
    isWorking = signal(false);

    username: string = '';

    selectedTab = 0;

    abonneForm: FormGroup;

    searchNumPolice: string = '';
    abonneTrouve?: Abonne;

    abonneListFull: Abonne[] = []; // ta liste complète d'abonnés
    abonneListFiltered: Abonne[] = []; // liste filtrée utilisée dans le tableau

    constructor() {
        this.abonneForm = this.fb.group({
            numPol: [''],
            nom: ['', [Validators.required, this.noNumbersValidator]],
            prenom: ['', [Validators.required, this.noNumbersValidator]],
            dateNaissance: ['', Validators.required],
            email: ['', [Validators.required, Validators.email]],
            genre: ['', Validators.required],
            dateCNIB: ['', Validators.required],
            numReccm: [''],
            numIfu: [''],
            numCNIB: ['', Validators.required],
            postbp: ['', Validators.required],
            telSer: [''],
            telWhatsapp: ['', Validators.required],
            ville: ['', Validators.required],
            raisonSocial: [''],
            exploitation: [null as number | null, Validators.required],
            dateCreation: [{ value: '', disabled: true }]
        });
    }

    private noNumbersValidator(control: AbstractControl): ValidationErrors | null {
        return /^[^\d]*$/.test(control.value) ? null : { numbersNotAllowed: true };
    }

    async ngOnInit() {
        await this.loadInitialData();
        const user = this.keycloakService.getInstance().tokenParsed;
        const firstName = user?.['given_name'] || '';
        const lastName = user?.['family_name'] || '';
        this.username = `${firstName} ${lastName}`.trim() || 'Utilisateur';

        this.abonneListFull = [];
        this.abonneListFiltered = this.abonneListFull;
    }

    private async loadInitialData() {
        try {
            const [abonnes, typeClients, exploitations] = await Promise.all([this.abonneService.getAllAbonnes(), this.typeClientService.getAllTypeClients(), this.exploitationService.getAllExploitation()]);
            this.abonneList.set(abonnes);
            this.typeClientList.set(typeClients);
            this.exploitationList.set(exploitations);

            this.abonneListFull = abonnes;
            this.abonneListFiltered = [...abonnes];
        } catch (error) {
            this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Erreur de chargement des données', life: 3000 });
        }
    }

    closeForm() {
        this.abonneForm.reset({
            numPol: '',
            nom: '',
            prenom: '',
            dateNaissance: '',
            email: '',
            genre: '',
            dateCNIB: '',
            numReccm: '',
            numIfu: '',
            numCNIB: '',
            postbp: '',
            telSer: '',
            telWhatsapp: '',
            ville: '',
            raisonSocial: '',
            exploitation: null
        });
        this.formVisible.set(false);
    }

    isCurrentTabValid(): boolean {
        const champsPhysique = ['nom', 'prenom', 'numCNIB', 'dateNaissance', 'email', 'genre', 'dateCNIB', 'postbp', 'telWhatsapp', 'ville', 'exploitation'];

        const champsMorale = ['raisonSocial', 'numReccm', 'numIfu', 'telSer', 'telWhatsapp', 'ville', 'exploitation'];

        const champs = this.selectedTab === 0 ? champsPhysique : this.selectedTab === 1 ? champsMorale : [];

        return champs.every((champ) => this.abonneForm.get(champ)?.valid);
    }

    searchByNumPolice() {
        if (!this.searchNumPolice) {
            return;
        }

        this.abonneService
            .getAbonneByNumPolice(this.searchNumPolice)
            .then((abonne) => {
                this.abonneTrouve = abonne;
                console.log('Abonné trouvé :', abonne);
            })
            .catch((error) => {
                console.error('Aucun abonné trouvé', error);
                this.abonneTrouve = undefined;
            });
    }
    searchTimeout: any;

    async onSearchNumPoliceChange(value: string) {
        console.log('Recherche numéro de police :', value);

        // Si on a déjà des abonnés en cache
        if (this.abonneListFull.length !== 0) {
            this.abonneListFiltered = this.abonneListFull.filter((ab) => ab.numPol === value);
            return;
        }

        // Sinon, on récupère depuis le service
        try {
            const abonnes = await this.abonneService.getAllAbonnes();
            this.abonneListFull = abonnes ?? [];
            this.abonneListFiltered = this.abonneListFull.filter((ab) => ab.numPol === value);
            console.log('Abonné trouvé:', this.abonneListFiltered);
        } catch (error) {
            console.log('Erreur lors de la récupération des abonnés :', error);
            this.abonneListFiltered = [];
        }
    }

    async submitAbonneForm() {
        this.isWorking.set(true);

        try {
            const username = this.keycloakService.getInstance().tokenParsed?.['preferred_username'] || '';

            const formData = this.abonneForm.value;
            const isUpdate = !!formData.numPol;

            if (!isUpdate) {
                const request: NewAbonneRequest = {
                    nom: formData.nom,
                    prenom: formData.prenom,
                    dateNaissance: formData.dateNaissance,
                    email: formData.email,
                    genre: formData.genre,
                    dateCNIB: formData.dateCNIB,
                    numReccm: formData.numReccm,
                    numIfu: formData.numIfu,
                    numCNIB: formData.numCNIB,
                    postbp: formData.postbp,
                    telSer: formData.telSer,
                    telWhatsapp: formData.telWhatsapp,
                    ville: formData.ville,
                    raisonSocial: formData.raisonSocial,
                    exploitationCode: formData.exploitation,
                    numPol: ''
                };

                const newAbonne = await this.abonneService.createAbonne(request);
                this.abonneList.update((list) => [...list, newAbonne]);
                this.logService.createLog(LogType.CREATE, EntityName.ABONNE, username, newAbonne.numPol, "Création d'un abonné").subscribe();
            } else {
                const updatedAbonne: Abonne = {
                    ...(formData as Abonne)
                };
                await this.abonneService.updateAbonne(updatedAbonne);
                this.abonneList.update((list) => list.map((a) => (a.numPol === formData.numPol ? { ...a, ...formData } : a)));
                this.logService.createLog(LogType.UPDATE, EntityName.ABONNE, username, updatedAbonne.numPol, "Modification d'un abonné").subscribe();
            }

            this.closeForm();
            this.messageService.add({ severity: 'info', summary: 'Réussie', detail: 'Opération terminée', life: 3000 });
        } catch (error) {
            console.error('Erreur :', error);
            this.messageService.add({ severity: 'error', summary: 'Erreur', detail: "Erreur durant l'opération", life: 3000 });
        } finally {
            this.isWorking.set(false);
        }
    }

    updateAbonne(abonne: Abonne) {
        this.abonneForm.patchValue(abonne);
        this.formVisible.set(true);
    }

    deleteAbonne(event: Event, abonne: Abonne) {
        this.confirmationService.confirm({
            target: event.target as HTMLElement,
            message: 'Voulez-vous supprimer cet abonné ?',
            icon: 'pi pi-exclamation-triangle',
            rejectButtonProps: { label: 'NON', severity: 'secondary', outlined: true },
            acceptButtonProps: { label: 'OUI' },
            accept: async () => {
                try {
                    const username = this.keycloakService.getInstance().tokenParsed?.['preferred_username'] || '';

                    await this.abonneService.deleteAbonne(abonne.numPol);
                    this.abonneList.update((list) => list.filter((a) => a.numPol !== abonne.numPol));
                    this.messageService.add({ severity: 'info', summary: 'Succès', detail: 'Suppression réussie', life: 3000 });

                    this.logService.createLog(LogType.DELETE, EntityName.ABONNE, username, abonne.numPol, "Suppression d'un abonné").subscribe();
                } catch (error) {
                    this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Échec de la suppression', life: 3000 });
                }
            }
        });
    }
}
