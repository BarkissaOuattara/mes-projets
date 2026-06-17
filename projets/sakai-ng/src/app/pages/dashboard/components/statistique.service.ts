import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface StatistiqueJour {
  date: string; // ISO format: 'yyyy-MM-dd'
  totalAbonnes: number;
  totalAbonnements: number;
  totalResiliations: number;
}

export interface StatistiqueMois {
  mois: string; // Format 'yyyy-MM'
  totalAbonnes: number;
  totalAbonnements: number;
  totalResiliations: number;
}

@Injectable({
  providedIn: 'root'
})
export class StatistiqueService {
  
  private readonly apiUrl = 'http://localhost:5000/statistique';

  constructor(private http: HttpClient) {}

  /**
   * Récupère les statistiques des X derniers jours
   * @param nbJours nombre de jours à récupérer
   */
  getStatsDerniersJours(nbJours: number): Observable<StatistiqueJour[]> {
    return this.http.get<StatistiqueJour[]>(`${this.apiUrl}/jours/${nbJours}`);
  }

  /**
   * Récupère toutes les statistiques mensuelles
   */
  getStatsMensuelles(): Observable<StatistiqueMois[]> {
    return this.http.get<StatistiqueMois[]>(`${this.apiUrl}/mois`);
  }

  /**
   * Initialise les statistiques pour les X derniers jours
   */
  initStatsDerniersJours(nbJours: number): Observable<void> {
    return this.http.get<void>(`${this.apiUrl}/init/jours/${nbJours}`);
  }

  /**
   * Initialise les statistiques du mois courant
   */
  initStatMensuelle(): Observable<void> {
    return this.http.get<void>(`${this.apiUrl}/init/mois`);
  }
}
