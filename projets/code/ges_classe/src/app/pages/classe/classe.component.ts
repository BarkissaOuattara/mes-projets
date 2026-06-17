import { Component, inject, OnInit, signal } from '@angular/core';
import { TableModule } from 'primeng/table';
import { Classe } from './classe.model';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ToastModule } from 'primeng/toast';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ClasseService } from './classe.service';

type FormDataType = {
    id: string, nom: string
}

@Component({
    selector: 'classe',
    templateUrl: './classe.component.html',
    imports: [
        TableModule,
        ButtonModule,
        DialogModule,
        InputTextModule,
        FormsModule,
        ReactiveFormsModule,
        ToastModule,
        ConfirmPopupModule
    ],
    providers: [
        ConfirmationService,
        MessageService,
        ClasseService
    ]
})
export class ClasseComponent implements OnInit {
    private readonly fb = inject(FormBuilder);

    private readonly confirmationService = inject(ConfirmationService);
    private readonly messageService = inject(MessageService);

    readonly classeService = inject(ClasseService);

    classeList = signal<Classe[]>([]);

    formVisible = signal<boolean>(false);
    classeForm: FormGroup;

    nomClasse = signal<string>("");

    constructor() {
        this.classeForm = this.fb.group({
            id: [undefined],
            nom: ["", Validators.required]
        });

        this.classeService.getAllClasses()
        .then(r => {
            this.classeList.set(r);
        })
        .catch(err => console.log(err));
        console.log("END constructor");
    }

    closeForm(){
        this.classeForm.patchValue({
            id: undefined,
            nom: ""
        });
        this.formVisible.set(false);
    }

    async submitClassForm(){
        try {
            const formData = this.classeForm.value as FormDataType;
            if(formData.id){
                //mise a jour
                await this.classeService.updateClasse(formData);
                this.classeList.update(val => {
                    const index = val.findIndex(cl => cl.id==formData.id);
                    if(index!=-1){
                        val[index].nom=formData.nom;
                    }
                    return val;
                });
            }else{
                //creation de classe
                const newClasse = await this.classeService.addClasse(formData.nom);
                this.classeList.update(val => {
                    val.push(newClasse);
                    return val;
                });
            }
            this.closeForm();
            this.messageService.add({ severity: 'info', summary: 'Reussie', detail: 'Opération terminée', life: 3000 });
        } catch (error) {
            this.messageService.add({ severity: 'error', summary: 'Echec', detail: 'Echec de la suppression', life: 3000 });
        }
    }

    updateClasse(classe: Classe){
        this.classeForm.patchValue({
            id: classe.id,
            nom: classe.nom
        });
        this.formVisible.set(true);
    }

    deleteClasse(event: Event, classe: Classe) {
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
                    await this.classeService.deleteClasse(classe.id);
                    this.classeList.update(val => val.filter(cl => cl.id!=classe.id));
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
