import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { Classe, ClasseDTO } from './classe.model';

@Injectable({
    providedIn: 'root'
})
export class ClasseService {
    
    readonly http = inject(HttpClient)
    classeUrl = "http://localhost:5000/classe"


    getAllClasses(): Promise<Classe[]>{
        return firstValueFrom(
            this.http.get<Classe[]>(this.classeUrl)
        )
    }

    addClasse(nom : String): Promise<Classe>{
        return firstValueFrom(
            this.http.put<Classe>(this.classeUrl, {nom:nom})
        )
    }

    updateClasse(classe : ClasseDTO){
        return firstValueFrom(
            this.http.post<{message : String}>(this.classeUrl, classe)
        )
    }

    deleteClasse(id : String){
        return firstValueFrom(
            this.http.delete<{message : String}>(`${this.classeUrl}?id=${id}`)
        )

    }
}