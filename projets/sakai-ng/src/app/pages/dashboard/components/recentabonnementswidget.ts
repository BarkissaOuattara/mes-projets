import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { AbonnementService } from '../../abonnement/abonnement.service';
import { Abonnement } from '../../abonnement/abonnement.model';

@Component({
  standalone: true,
  selector: 'app-recent-abonnements-widget',
  imports: [CommonModule, TableModule, ButtonModule, RippleModule ],
  template: `
    <div class="card !mb-8">
      <div class="font-semibold text-xl mb-4" class="text-5xl">10 Derniers Abonnements</div>
      <p-table [value]="abonnements" [paginator]="false" responsiveLayout="scroll">
        <ng-template pTemplate="header">
           <tr class="text-2xl">
                <th style="min-width: 16rem">Numéro Police</th>
                <th style="min-width: 16rem">Nom</th>
                <th style="min-width: 16rem">Prénom</th>
                <th style="min-width: 16rem">Lot</th>
                <th style="min-width: 16rem">Parcelle</th>
                <th style="min-width: 16rem">Rang</th>
                <th style="min-width: 16rem">Section</th>
                <th style="min-width: 16rem">Tarif</th>
                <th style="min-width: 16rem">Type Branch</th>
                <th style="min-width: 16rem">Code Exploitation</th>
                <th style="min-width: 16rem">Statut</th>
                <th style="min-width: 16rem">Date Début</th>
                <th style="min-width: 16rem">Date Résiliation</th>
            </tr>
        </ng-template>
        <ng-template pTemplate="body" let-abonnement>
           <tr>
                <td>{{ abonnement.numPol }}</td>
                <td>{{ abonnement.abonne?.nom }}</td>
                <td>{{ abonnement.abonne?.prenom }}</td>
                <td>{{ abonnement.branchement?.lot }}</td>
                <td>{{ abonnement.branchement?.parcelle }}</td>
                <td>{{ abonnement.branchement?.rang }}</td>
                <td>{{ abonnement.branchement?.section }}</td>
                <td>{{ abonnement.tarif?.libelle }}</td>
                <td>{{ abonnement.branchement?.typeBranch?.libelle || '-' }}</td>
                <td>{{ abonnement.branchement?.exploitation?.code || '-' }}</td>
                <td>{{ abonnement.statut }}</td>
                <td>{{ abonnement.dateDebut | date: 'yyyy-MM-dd' }}</td>
                <td>{{ abonnement.dateResiliation ? (abonnement.dateResiliation | date: 'yyyy-MM-dd') : '-' }}</td>
                
            </tr>
        </ng-template>
      </p-table>
    </div>
  `})
export class RecentAbonnementsWidgetComponent {
  abonnements: Abonnement[] = [];

  constructor(private abonnementService: AbonnementService) {}

  ngOnInit() {
        this.abonnementService.getDerniersAbonnements().subscribe(data => {
    this.abonnements = data.filter(abonnement => abonnement.statut !== 'W');
  });
  }
}
