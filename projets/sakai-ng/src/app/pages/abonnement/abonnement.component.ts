import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MessageService, ConfirmationService } from 'primeng/api';
import { TableModule } from 'primeng/table';
import { Table } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { InputTextModule } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';
import { RadioButtonModule } from 'primeng/radiobutton';
import { InputNumberModule } from 'primeng/inputnumber';
import { DialogModule } from 'primeng/dialog';
import { TagModule } from 'primeng/tag';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { IconFieldModule } from 'primeng/iconfield';
import { RatingModule } from 'primeng/rating';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { DropdownModule } from 'primeng/dropdown';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { InputIconModule } from 'primeng/inputicon';
import { SelectModule } from 'primeng/select';

import { AbonnementService } from './abonnement.service';
import { Abonnement, AbonnementDTO } from './abonnement.model';
import { Exploitation } from '../../share/exploitation/exploitation.model';
import { ExploitationService } from '../../share/exploitation/exploitation.service';
import { TarifService } from '../../share/tarif/tarif.service';
import { Abonne, AbonneDTO } from '../../share/abonne/abonne.model';
import { Tarif } from '../../share/tarif/tarif.model';
import { TypeBranch } from '../../share/typebranch/typebranch.model';
import { TypeBranchService } from '../../share/typebranch/typebranch.service';
import { AbonneService } from '../../share/abonne/abonne.service';
import { Branchement } from '../../share/branchement/branchement.model';
import { KeycloakService } from '../../config/keycloak-config/keycloak.service';
import { LogService } from '../../share/log/log.service';
import { EntityName, LogType } from '../../share/log/app-logs.model';
import { lastValueFrom } from 'rxjs';

interface Column {
    field: string;
    header: string;
    customExportHeader?: string;
}

interface ExportColumn {
    title: string;
    dataKey: string;
}

@Component({
    selector: 'app-abonnement',
    standalone: true,
    templateUrl: 'abonnement.component.html',
    providers: [MessageService, ConfirmationService, TarifService, AbonneService],
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
    ]
})
export class AbonnementComponent implements OnInit {
    abonnementDialog = false;
    resiliationDialog = false;
    numPolRecherche: string = '';
    abonnements: AbonnementDTO[] = [];

    private readonly logService = inject(LogService);
    private readonly keycloakService = inject(KeycloakService);

    abonnementList = signal<AbonnementDTO[]>([]);
    exploitationList = signal<Exploitation[]>([]);
    readonly typeBranchList = signal<TypeBranch[]>([]);
    readonly tarifList = signal<Tarif[]>([]);
    readonly abonneList = signal<Abonne[]>([]);

    username: string = '';

    searchNumPolice: string = '';
    abonnementTrouve?: AbonnementDTO;

    abonne: Abonne | null = null;
    abonneDTO: AbonneDTO | null = null;
    typeBranch: TypeBranch | null = null;
    tarif: Tarif | null = null;

    abonnement!: Abonnement;
    branchement!: Branchement;
    selectedAbonnement: Abonnement[] | null = null;
    isWorking = false;
    isLoadingResiliationData = false;
    submitted = false;

    statutOptions!: { label: string; value: string }[];
    abonnementForm!: FormGroup;
    resiliationForm!: FormGroup;
    branchementForms!: FormGroup;
    abonneForms!: FormGroup;

    cols: Column[] = [];
    exportColumns: ExportColumn[] = [];

    branchementload = signal(false);
    abonneload = signal(false);

    abonnementListFull: AbonnementDTO[] = []
    abonnementListFiltered : AbonnementDTO[]= []

    constructor(
        private fb: FormBuilder,
        private abonnementService: AbonnementService,
        private exploitationService: ExploitationService,
        private typeBranchService: TypeBranchService,
        private tarifService: TarifService,
        private abonneService: AbonneService,
        private messageService: MessageService,
        private confirmationService: ConfirmationService
    ) {}

    ngOnInit(): void {
        this.initForms();
        this.loadAbonnements();
        this.initLists();
        const user = this.keycloakService.getInstance().tokenParsed;
        const firstName = user?.['given_name'] || '';
        const lastName = user?.['family_name'] || '';
        this.username = `${firstName} ${lastName}`.trim() || 'Utilisateur';

        this.statutOptions = [
            { label: 'Actif', value: 'A' },
            { label: 'Résilié', value: 'W' }
        ];

        this.cols = [
            { field: 'numPol', header: 'Numéro Police' },
            { field: 'codeBranch', header: 'Code Branchement' },
            { field: 'statut', header: 'Statut' },
            { field: 'dateDebut', header: 'Date Début' },
            { field: 'dateResiliation', header: 'Date Résiliation' }
        ];

        this.exportColumns = this.cols.map((col) => ({ title: col.header, dataKey: col.field }));
    }

    abonnementsFull: AbonnementDTO[] = [];

    searchByNumPolice() {
        if (!this.searchNumPolice) {
            return;
        }

        this.abonnementService.getAbonnementByNumPolice(this.searchNumPolice).subscribe({
            next: (abonnement: AbonnementDTO) => {
                this.abonnementTrouve = abonnement;
                console.log('abonnement trouvé :', abonnement);
            },
            error: (error) => {
                console.error('Aucun abonnement trouvé', error);
                this.abonnementTrouve = undefined;
            }
        });
    }

    searchTimeout: any;

   async onSearchNumPoliceChange(value: string) {
  console.log('Recherche numéro de police :', value);

  // Si on a déjà des abonnés en cache
  if (this.abonnementListFull.length !== 0) {
    this.abonnementListFiltered = this.abonnementListFull.filter(ab => ab.numPol === value);
    return;
  }

  // Sinon, on récupère depuis le service
  try {
    this.abonnementListFull = await lastValueFrom(this.abonnementService.getAllAbonnements());
    this.abonnementListFiltered = this.abonnementListFull.filter(ab => ab.numPol === value);
    console.log('Abonné trouvé:', this.abonnementListFiltered);
  } catch (error) {
    console.log('Erreur lors de la récupération des abonnés :', error);
    this.abonnementListFiltered = [];
  }
}


    private initForms(): void {
        this.abonnementForm = this.fb.group({
            codeExpl: ['', Validators.required],
            lot: ['', Validators.required],
            parcelle: ['', Validators.required],
            section: ['', Validators.required],
            rang: ['', Validators.required],
            statut: ['', Validators.required],
            numPol: ['', Validators.required],
            typeBranchement: [null, Validators.required],
            tarif: [null, Validators.required],
            nom: [''],
            prenom: ['']
        });

        this.abonneForms = this.fb.group({
            numPol: ['', Validators.required]
        });

        this.resiliationForm = this.fb.group({
            codeExpl: [''],
            lot: [''],
            parcelle: [''],
            section: [''],
            rang: [''],
            statut: ['', Validators.required],
            numPol: ['']
        });
    }

    private async initLists(): Promise<void> {
        try {
            const [exploitations, typeBranches, tarifs, abonne] = await Promise.all([this.exploitationService.getAllExploitation(), this.typeBranchService.getAllTypeBranch(), this.tarifService.getAllTarifs(), this.abonneService.getAllAbonnes()]);
            this.exploitationList.set(exploitations);
            this.typeBranchList.set(typeBranches);
            this.tarifList.set(tarifs);
            this.abonneList.set(abonne);
        } catch (error) {
            this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Erreur lors du chargement des listes' });
        }
    }

    chercherBranchement() {
        this.branchementload.set(false);
        const branchement = {
            codeExpl: this.abonnementForm.value?.codeExpl,
            lot: this.abonnementForm.value?.lot,
            parcelle: this.abonnementForm.value?.parcelle,
            section: this.abonnementForm.value?.section,
            rang: this.abonnementForm.value?.rang
        };
        console.log(branchement);
        this.abonnementService.getBranchement(branchement.codeExpl, branchement.lot, branchement.parcelle, branchement.section, branchement.rang).subscribe((branchement) => {
            console.log(branchement);
            if (branchement) {
                this.branchement = branchement;
                this.branchementload.set(true);
            }
        });
    }

    chercherAbonne() {
        this.abonneload.set(false);
        const abonne = {
            numPol: this.abonnementForm.value?.numPol
        };
        console.log(abonne);
        this.abonnementService.getAbonneByNumPolice(abonne.numPol).subscribe((abonne) => {
            console.log(abonne);
            if (abonne) {
                this.abonneDTO = abonne;
                this.abonnementForm.patchValue({
                    nom: abonne.nom,
                    prenom: abonne.prenom
                });
                this.abonnementForm.get('nom')?.disable();
                this.abonnementForm.get('prenom')?.disable();
                this.abonneload.set(true);
            }
        });
    }
    loadInfosCreation(): void {
        const { codeExpl, lot, parcelle, section, rang, numPol } = this.abonnementForm.value;
        if (!codeExpl || !lot || !parcelle || !section || !rang || !numPol) {
            this.messageService.add({ severity: 'warn', summary: 'Statut incorrect', detail: 'Ce branchement n\'existe pas ou une information a été mal saisie' });
            return;
        }
        
        this.isWorking = true;
        this.abonnementService.getInfosPourCreation(numPol, codeExpl, lot, parcelle, section, rang).subscribe({
            next: (data: Abonnement) => {
                this.abonne = data.abonne;
                this.typeBranch = data.typeBranch;
                this.tarif = data.typeBranch?.tarif ?? null;
                this.abonnementForm.patchValue({
                    nomAbonne: this.abonne?.nom,
                    prenomAbonne: this.abonne?.prenom,
                    libelleTypeBranchement: this.typeBranch?.libelle,
                    codeTarif: this.tarif?.code,
                    libelleTarif: this.tarif?.libelle
                });
            },
            error: () => {
                this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Impossible de charger les informations d’abonnement.' });
            },
            complete: () => (this.isWorking = false)
        });
    }

    loadAbonnements(): void {
        this.abonnementService.getAllAbonnements().subscribe({
            next: (data: AbonnementDTO[]) => this.abonnementList.set(data),
            error: () => this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Impossible de charger les abonnements' })
        });
    }

    onGlobalFilter(table: Table, event: Event): void {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    openNew(): void {
        this.branchementload.set(false);
        this.abonnementForm.reset();
        this.abonnement = {} as Abonnement;
        this.submitted = false;
        this.abonnementDialog = true;
    }

    hideDialog(): void {
        this.abonnementDialog = false;
        this.submitted = false;
    }

    hideResiliationDialog(): void {
        this.resiliationDialog = false;
        this.submitted = false;
        this.resiliationForm.reset();
    }

    getSeverity(status: string): string {
        switch (status) {
            case 'A':
                return 'success';
            case 'W':
                return 'danger';
            default:
                return 'info';
        }
    }

    saveAbonnement(): void {
        this.submitted = true;
        if (this.abonnementForm.invalid) {
            console.log(this.abonnementForm.value);
            return;
        }
        const username = this.keycloakService.getInstance().tokenParsed?.['preferred_username'] || '';

        console.log('form');

        const formValue = this.abonnementForm.value;

        console.log(formValue);

        this.branchement.typeBranchement = formValue.typeBranchement;
        const abonnement: AbonnementDTO = {
            numPol: formValue.numPol,
            statut: formValue.statut,
            abonne: this.abonneDTO,
            branchement: this.branchement,
            tarif: formValue.tarif
        };

        console.log(abonnement);
        this.isWorking = true;

        this.abonnementService.createAbonnement(abonnement).subscribe({
            next: (createdAbonnement) => {
                this.logService.createLog(LogType.CREATE, EntityName.ABONNEMENT, username, createdAbonnement.numPol!, "Création d'un abonnement").subscribe();
                this.messageService.add({ severity: 'success', summary: 'Créé', detail: 'Abonnement créé' });
                this.abonnementDialog = false;
                this.loadAbonnements();
            },
            error: () => {
                this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Échec de la création' });
            },
            complete: () => (this.isWorking = false)
        });
    }

    resilierAbonnement(abonnement: Abonnement): void {
        this.resiliationDialog = true;
        this.abonnement = abonnement;
        this.resiliationForm.patchValue({
            code: abonnement.code,
            codeExpl: abonnement.branchement?.exploitation?.code,
            lot: abonnement.branchement?.lot,
            parcelle: abonnement.branchement?.parcelle,
            section: abonnement.branchement?.section,
            rang: abonnement.branchement?.rang,
            numPol: abonnement.numPol,
            statut: abonnement.statut
        });
        this.resiliationForm.get('codeExpl')?.disable();
        this.resiliationForm.get('lot')?.disable();
        this.resiliationForm.get('parcelle')?.disable();
        this.resiliationForm.get('section')?.disable();
        this.resiliationForm.get('rang')?.disable();
        this.resiliationForm.get('numPol')?.disable();
        console.log(this.resiliationForm.value);
    }

    submitResiliation(): void {
        if (this.resiliationForm.invalid) {
            this.messageService.add({
                severity: 'error',
                summary: 'Formulaire invalide',
                detail: 'Veuillez remplir tous les champs requis.'
            });
            return;
        }

        const statut = this.resiliationForm.get('statut')?.value;
        if (statut !== 'W') {
            this.messageService.add({
                severity: 'warn',
                summary: 'Statut incorrect',
                detail: 'Veuillez d\'abord changer le statut à "Résilié (W)" avant de procéder.'
            });
            return;
        }
        const username = this.keycloakService.getInstance().tokenParsed?.['preferred_username'] || '';
        if (this.abonnement.code) {
            const code = this.abonnement.code;
            this.abonnementService.resilierAbonnement(code).subscribe({
                next: () => {
                    this.logService.createLog(LogType.RESILIATION, EntityName.ABONNEMENT, username, this.abonnement.numPol, "Resiliation d'un abonnement").subscribe();
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Succès',
                        detail: 'Abonnement résilié avec succès.'
                    });
                    this.resiliationDialog = false;
                    this.loadAbonnements(); // recharge la liste à jour
                },
                error: (err) => {
                    console.error('Erreur lors de la résiliation :', err);
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Erreur',
                        detail: 'Échec de la résiliation.'
                    });
                },
                complete: () => {
                    this.isLoadingResiliationData = false;
                }
            });
        } else {
            this.messageService.add({
                severity: 'error',
                summary: 'Formulaire invalide',
                detail: 'Resiliation impossible'
            });
        }
    }
}
