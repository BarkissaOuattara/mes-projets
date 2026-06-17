import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppLogs, LogType, EntityName } from './app-logs.model';

@Injectable({
  providedIn: 'root'
})
export class LogService {
  private apiUrl = 'http://localhost:5000/log';

  constructor(private http: HttpClient) {}

  /**
   * Récupère tous les logs enregistrés
   */
  getAllLogs(): Observable<AppLogs[]> {
    return this.http.get<AppLogs[]>(this.apiUrl);
  }

  /**
   * Crée un log générique pour toute entité
   */
  createLog(
    logType: LogType,
    entityName: EntityName,
    authorCode: string,
    codeObject: string,
    message: string
  ): Observable<AppLogs> {
    const log: AppLogs = {
      dateTime: new Date(),
      logType,
      entityName,
      authorCode,
      codeObject,
      message
    };

    return this.http.put<AppLogs>(this.apiUrl, log);
  }
}
