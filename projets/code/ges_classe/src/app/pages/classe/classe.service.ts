import { inject, Injectable, signal } from '@angular/core';
import { Classe } from './classe.model';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

@Injectable()
export class ClasseService {

    readonly http = inject(HttpClient);
    classeUrl = "http://localhost:5000/classe";

    /**
     * Retourne la liste des classes
     * @returns Promise<Classe[]>
     */
    getAllClasses(): Promise<Classe[]>{
        return firstValueFrom(
            this.http.get<Classe[]>(this.classeUrl)
        );
    }

    addClasse(nom: string): Promise<Classe>{
        return firstValueFrom(
            this.http.put<Classe>(this.classeUrl, {nom: nom})
        );
    }

    updateClasse(classe: Classe){
        return firstValueFrom(
            this.http.post<{message: string}>(this.classeUrl, classe)
        );
    }

    deleteClasse(id: string){
        return firstValueFrom(
            this.http.delete<{message: string}>(`${this.classeUrl}?id=${id}`)
        );
    }
}