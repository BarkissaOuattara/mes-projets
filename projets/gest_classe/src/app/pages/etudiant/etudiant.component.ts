import { Component, inject, OnInit, signal } from '@angular/core';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ToastModule } from 'primeng/toast';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Etudiant } from './etudiant.model';
import { EtudiantService } from './etudiant.service';
import { ClasseService } from '../classe/classe.service';
import { Classe } from '../classe/classe.model';
import { SelectModule } from 'primeng/select';
import { ProgressSpinnerModule } from 'primeng/progressspinner';


type FormDataType = {
    id : string,
    matricule : string,
    nom : string,
    prenom : string
    classe: Classe
}

@Component({
    selector:"etudiant",
    templateUrl : "etudiant.component.html",
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
        ProgressSpinnerModule
        
        ],
    providers: [
            ConfirmationService,
            MessageService,
            EtudiantService
        ]
})

export class EtudiantComponent implements OnInit {

    private readonly fb = inject(FormBuilder);

    private readonly confirmationService = inject(ConfirmationService);

    private readonly messageService = inject(MessageService);

    readonly etudiantService = inject(EtudiantService);

    readonly classeService = inject(ClasseService);

    classeList = signal<Classe[]>([]);

    etudiantList = signal<Etudiant[]>([]);

    formVisible = signal<boolean>(false);

    isWorking = signal<boolean>(false);

    classeForm: FormGroup;

    matriculeEtudiant = signal<string>("");
    nomEtudiant = signal<string>("");
    prenomEtudiant = signal<string>("");

constructor() {

    this.init();

        this.classeForm = this.fb.group({
            id: [undefined],
            matricule: ["", Validators.required],
            nom: ["", Validators.required],
            prenom: ["", Validators.required],
            classe: [undefined, Validators.required]
        });


}
    async init() {
        this.etudiantList.set(
            await this.etudiantService.getAllEtudiants()
        );

        this.classeList.set(
            await this.classeService.getAllClasses()
        )


        
        
    }

    closeForm(){
        this.classeForm.patchValue({
            id: undefined,
            matricule: "",
            nom: "",
            prenom: "",
            classe: undefined

                });
        this.formVisible.set(false);
    }

    async submitClassForm() {

        this.isWorking.set(true);

        try {
            const formData = this.classeForm.value as FormDataType;
    
            if (formData.id) {
                // Mise à jour
                await this.etudiantService.updateEtudiant(formData);
                this.etudiantList.update(val => {
                    const index = val.findIndex(cl => cl.id === formData.id);
                    if (index !== -1) {
                        val[index].matricule = formData.matricule;
                        val[index].nom = formData.nom;
                        val[index].prenom = formData.prenom;
                        val[index].classe = formData.classe;
                        
                    }
                    return val;
                });
            } else {
                // Création d'un etudiant
                const newEtudiant = await this.etudiantService.addEtudiant(
                    formData.matricule,
                    formData.nom,
                    formData.prenom,
                    formData.classe

                );
                this.etudiantList.update(val => {
                    val.push(newEtudiant);
                    return val;
                });
                
            }
    
            this.closeForm();
            this.messageService.add({severity: 'info',summary: 'Réussie',detail: 'Opération terminée',life: 3000 });
    
        } catch (error) {
            this.messageService.add({severity: 'error',summary: 'Échec',detail: 'Une erreur est survenue lors de l\'opération',life: 3000});
        }finally{
            this.isWorking.set(false);
        }
    }
    

    updateEtudiant(etudiant: Etudiant){
        this.classeForm.patchValue({
            id: etudiant.id,
            matricule: etudiant.matricule,
            nom: etudiant.nom,
            prenom: etudiant.prenom,
            classe: etudiant.classe
        });
        this.formVisible.set(true);
    }
    

    deleteClasse(event: Event, etudiant: Etudiant) {
        this.confirmationService.confirm({
            target: event.target as EventTarget,
            message: 'Voulez vous vraiment supprimer?',
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
                    await this.etudiantService.deleteEtudiant(etudiant.id);
                    this.etudiantList.update(val => val.filter(cl => cl.id!=etudiant.id));
                    this.messageService.add({ severity: 'info', summary: 'Reussie', detail: 'Suppression terminée', life: 3000 });
                } catch (error) {
                    this.messageService.add({ severity: 'error', summary: 'Echec', detail: 'Echec de la suppression', life: 3000 });
                }
            },
            reject: () => {}
        });
    }

    ngOnInit(): void { }
}
