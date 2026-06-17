import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { firstValueFrom, Observable } from "rxjs";
import { TypeBranch } from '../typebranch/typebranch.model';

@Injectable({
  providedIn: 'root'
})
export class TypeBranchService {
  private readonly http = inject(HttpClient);
  private readonly typebranchUrl = "http://localhost:5000/typebranch";

  // Récupérer tous les TypeBranch
  getAllTypeBranch(): Promise<TypeBranch[]> {
    return firstValueFrom(
      this.http.get<TypeBranch[]>(this.typebranchUrl)
    );
  }

  // Créer un nouveau TypeBranch
  createTypeBranch(typeBranch: TypeBranch): Observable<TypeBranch> {
    return this.http.put<TypeBranch>(this.typebranchUrl, typeBranch);
  }

  // Mettre à jour un TypeBranch existant
  updateTypeBranch(code: string, typeBranch: TypeBranch): Observable<TypeBranch> {
    return this.http.post<TypeBranch>(`${this.typebranchUrl}/${code}`, typeBranch);

  }

  // Supprimer un TypeBranch
  deleteTypeBranch(code: string): Observable<void> {
    return  this.http.delete<void>(`${this.typebranchUrl}/${code}`);
  }

  getTypeBranchByCode(code: string): Observable<TypeBranch> {
return this.http.get<TypeBranch>(`${this.typebranchUrl}/${encodeURIComponent(code)}`);
}

}
