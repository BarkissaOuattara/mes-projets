// branchement.service.ts
import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { Branchement } from "./branchement.model";
import { NewBranchementRequest } from "./newbranchement.model";

@Injectable()
export class BranchementService {
  readonly http = inject(HttpClient);
  branchementUrl = "http://localhost:5000/branchement";

  getAllBranchements(): Promise<Branchement[]> {
    return firstValueFrom(this.http.get<Branchement[]>(this.branchementUrl));
  }

  addBranchement(branchement: NewBranchementRequest): Promise<Branchement> {
    return firstValueFrom(
      this.http.put<Branchement>(this.branchementUrl, branchement)
    );
  }

  updateBranchement(branchement: Branchement) {
    return firstValueFrom(
      this.http.post<{ message: string }>(this.branchementUrl, branchement)
    );
  }

  deleteBranchement(code: string) {
    return firstValueFrom(
      this.http.delete<{ message: string }>(`${this.branchementUrl}/${code}`)
    );
  }
  

  searchBranchement(lot: number, parcelle: number, rang: number, section: string, codeExpl: number): Promise<Branchement[]> {
  const params = new URLSearchParams({
    lot: lot.toString(),
    parcelle: parcelle.toString(),
    rang: rang.toString(),
    section,
    codeExpl: codeExpl.toString()
  });
  return firstValueFrom(
    this.http.get<Branchement[]>(`${this.branchementUrl}/search?${params.toString()}`)
  );
}

}
