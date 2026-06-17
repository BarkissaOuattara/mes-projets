import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Abonnement, AbonnementDTO } from './abonnement.model';
import { Resiliation } from './resiliation.model';
import { Branchement } from '../../share/branchement/branchement.model';
import { Abonne, AbonneDTO } from '../../share/abonne/abonne.model';

@Injectable({
  providedIn: 'root'
})
export class AbonnementService {
  private apiUrl = 'http://localhost:5000/abonnement';

  constructor(private http: HttpClient) {}

  /** Récupère tous les abonnements */
  getAllAbonnements(): Observable<AbonnementDTO[]> {
  return this.http.get<AbonnementDTO[]>(this.apiUrl);
}


  /** Crée un nouvel abonnement */
  createAbonnement(abonnement: AbonnementDTO): Observable<Abonnement> {
    return this.http.put<Abonnement>(this.apiUrl, abonnement);
  }
  

    /** Résilie un abonnement existant */
  resilierAbonnement(code: string): Observable<Resiliation> {
    const requestBody = { code : code};
    return this.http.post<Resiliation>(`${this.apiUrl}/resilier`, requestBody);
  }

  /** Récupère un abonné par numéro de police */
  getAbonneByNumPolice(numPol: number): Observable<AbonneDTO> {
    return this.http.get<AbonneDTO>(`${this.apiUrl}/${numPol}`);
  }

  /** Récupère les informations d’un branchement */
  getBranchement(codeExpl: number, lot: number, parcelle: number, section: string, rang: number): Observable<Branchement> {
    return this.http.get<Branchement>(
      `${this.apiUrl}/branchement/${codeExpl}/${lot}/${parcelle}/${section}/${rang}`
    );
  }

  getAbonnementByNumPolice(numPol: string): Observable<AbonnementDTO> {
  return this.http.get<AbonnementDTO>(`${this.apiUrl}/by-police/${numPol}`);
}

  getDerniersAbonnements(): Observable<Abonnement[]> {
  return this.http.get<Abonnement[]>(`${this.apiUrl}/dernier`);
}

  /** Récupère un abonnement à résilier selon ses coordonnées complètes */
  getAbonnementPourResiliation(codeExpl: number, lot: number, parcelle: number, section: string, rang: number, numPol: number): Observable<Abonnement> {
    return this.http.get<Abonnement>(
      `${this.apiUrl}/resiliation/${codeExpl}/${lot}/${parcelle}/${section}/${rang}/${numPol}`
    );
  }

  /** Récupère les informations nécessaires à la création d’un abonnement */
  getInfosPourCreation(
    numPol: number,
    codeExpl: number,
    lot: number,
    parcelle: number,
    section: string,
    rang: number
  ): Observable<Abonnement> {
    return this.http.get<Abonnement>(
      `${this.apiUrl}/infos-creation/${codeExpl}/${lot}/${parcelle}/${section}/${rang}/${numPol}`
    );
  }

  
}

