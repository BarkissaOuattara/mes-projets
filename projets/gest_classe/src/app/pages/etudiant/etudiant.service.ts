import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { Etudiant } from "./etudiant.model";
import { Classe } from "../classe/classe.model";


@Injectable()
export class EtudiantService{
    readonly http = inject(HttpClient)
    etudiantUrl = "http://localhost:5000/etudiant"


getAllEtudiants(): Promise<Etudiant[]>{
        return firstValueFrom(
            this.http.get<Etudiant[]>(this.etudiantUrl)
        )
    }

    addEtudiant(matricule: String, nom: string, prenom: string, classe:Classe): Promise<Etudiant>{
        return firstValueFrom(
            this.http.put<Etudiant>(this.etudiantUrl,{matricule:matricule, nom:nom, prenom:prenom, classe:classe})
        )
    }

    updateEtudiant(etudiant : Etudiant){
        return firstValueFrom(
            this.http.post<{message : String}>(this.etudiantUrl, etudiant)
        )
    }

    deleteEtudiant(id : String){
        return firstValueFrom(
            this.http.delete<{message : String}>(`${this.etudiantUrl}?id=${id}`)
        )

    }
}