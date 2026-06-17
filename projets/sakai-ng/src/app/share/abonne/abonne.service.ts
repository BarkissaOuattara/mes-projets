import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { firstValueFrom, Observable } from 'rxjs';
import { Abonne } from './abonne.model';
import { NewAbonneRequest } from './newAbonne.model';

@Injectable()
export class AbonneService {
  readonly http = inject(HttpClient);
  abonneUrl = 'http://localhost:5000/abonne';

  // Récupérer tous les abonnés
  getAllAbonnes(): Promise<Abonne[]> {
    return firstValueFrom(this.http.get<Abonne[]>(this.abonneUrl));
  }

  // Créer un abonné
  createAbonne(abonne: NewAbonneRequest): Promise<Abonne> {
    return firstValueFrom(this.http.put<Abonne>(this.abonneUrl, abonne));
  }

  // Mettre à jour un abonné
  updateAbonne(abonne: NewAbonneRequest): Observable<any> {
    return this.http.post<void>(this.abonneUrl, abonne);
  }

  // Supprimer un abonné
  deleteAbonne(numPolice: string) {
    return firstValueFrom(
        this.http.delete<{ message: string }>(`${this.abonneUrl}/${numPolice}`));
  }

  // Rechercher un abonné par son numéro de police
getAbonneByNumPolice(numPolice: string): Promise<Abonne> {
  return firstValueFrom(this.http.get<Abonne>(`${this.abonneUrl}/${numPolice}`));
}

}
