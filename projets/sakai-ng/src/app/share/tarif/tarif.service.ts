import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { firstValueFrom, Observable } from 'rxjs';

import { Tarif } from './tarif.model';
import { NewTarifRequest } from './newtarif.model';

@Injectable()
export class TarifService {
  private readonly http = inject(HttpClient);
  private readonly tarifUrl = 'http://localhost:5000/tarif';

  // Récupère tous les tarifs
  getAllTarifs(): Promise<Tarif[]> {
    return firstValueFrom(this.http.get<Tarif[]>(this.tarifUrl));
  }

  // Ajoute un tarif
  addTarif(tarif: NewTarifRequest): Promise<Tarif> {
    return firstValueFrom(
      this.http.put<Tarif>(this.tarifUrl, tarif)
    );
  }

  // Met à jour un tarif existant
  updateTarif(tarif: Tarif){
    return firstValueFrom(
      this.http.post<{ message: string }>(this.tarifUrl, tarif)
    );
  }

  // Supprime un tarif
  deleteTarif(code: string){
    return firstValueFrom(
      this.http.delete<{ message: string }>(`${this.tarifUrl}/${code}`)
    );
  }

  getTarifByLibelle(libelle: string): Observable<Tarif> {
  return this.http.get<Tarif>(`${this.tarifUrl}/search?libelle=${encodeURIComponent(libelle)}`);
}

}

