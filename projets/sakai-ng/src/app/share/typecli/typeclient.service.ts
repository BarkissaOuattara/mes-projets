import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { firstValueFrom, Observable } from "rxjs";
import { TypeClient } from "./typeclient.model";

@Injectable()
export class TypeClientService {
  readonly http = inject(HttpClient);
  typeClientUrl = "http://localhost:5000/typecli";

  // Récupérer tous les types de clients
  getAllTypeClients(): Promise<TypeClient[]> {
    return firstValueFrom(this.http.get<TypeClient[]>(this.typeClientUrl));
  }

  // Créer un type de client
  addTypeClient(typeClient: TypeClient) {
    return firstValueFrom(
      this.http.put<TypeClient>(this.typeClientUrl, typeClient)
    );
  }

    // Mettre à jour un type de client
  updateTypeClient(typeClient: TypeClient) {
    if (!typeClient.code || typeClient.code.trim() === "") {
      throw new Error("Le code du type client est requis pour la mise à jour.");
    }

  return firstValueFrom(
    this.http.post<{ message: string }>(this.typeClientUrl, typeClient)
  );
}

  // Supprimer un type de client
  deleteTypeClient(code: string) {
    return firstValueFrom(
      this.http.delete<{ message: string }>(`${this.typeClientUrl}/${code}`)
    );
  }

  getTypeClientByLibelle(libelle: string): Observable<TypeClient> {
  return this.http.get<TypeClient>(`${this.typeClientUrl}/search?libelle=${encodeURIComponent(libelle)}`);
}

}
