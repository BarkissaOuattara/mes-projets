import { Component, OnInit } from '@angular/core';
import { ChartModule } from 'primeng/chart';
import { CommonModule } from '@angular/common';
import { StatistiqueJour, StatistiqueMois, StatistiqueService } from './statistique.service';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-chart-line-style',
  standalone: true,
  imports: [CommonModule, ChartModule, FormsModule],
  template: `
    <div class="card space-y-4">
      <select [(ngModel)]="mode" (change)="chargerDonnees()" class="p-2 border rounded">
        <option value="jour">7 Derniers jours</option>
        <option value="mois">Statistiques mensuelles</option>
      </select>

      <p-chart type="line" [data]="data" [options]="options" class="h-[30rem]" />
    </div>
  `
})
export class ChartLineStyle implements OnInit {
  data: any;
  options: any;
  mode: 'jour' | 'mois' = 'jour';

  constructor(private statsService: StatistiqueService) {}

  ngOnInit() {
    this.chargerDonnees();

    this.options = {
      responsive: true,
      plugins: {
        legend: {
          labels: { color: '#495057' }
        }
      },
      scales: {
        x: {
          ticks: { color: '#495057' },
          grid: { color: '#ebedef' },
          offset: false
        },
        y: {
          ticks: { color: '#495057' },
          grid: { color: '#ebedef' }
        }
      }
    };
  }

  chargerDonnees() {
    if (this.mode === 'jour') {
      this.statsService.getStatsDerniersJours(7).subscribe({
        next: (stats) => {
          if (stats && stats.length > 0) {
            this.initChartJour(stats);
          } else {
            this.initEmptyChart();
          }
        },
        error: () => this.initEmptyChart()
      });
    } else {
      this.statsService.getStatsMensuelles().subscribe({
        next: (stats) => {
          if (stats && stats.length > 0) {
            this.initChartMois(stats);
          } else {
            this.initEmptyChart();
          }
        },
        error: () => this.initEmptyChart()
      });
    }
  }

  private initChartJour(stats: StatistiqueJour[]) {
  // Trier les stats par date croissante
  stats.sort((a, b) => new Date(a.date).getTime() - new Date(b.date).getTime());

  const labels = stats.map(s => new Date(s.date).toLocaleDateString());
  const abonnements = stats.map(s => s.totalAbonnements);
  const resiliations = stats.map(s => s.totalResiliations);
  const abonnes = stats.map(s => s.totalAbonnes);

  this.setChartData(labels, abonnements, resiliations, abonnes);
}


  private initChartMois(stats: StatistiqueMois[]) {
  const moisOrdre = [
    'Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin',
    'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'
  ];

  stats.sort((a, b) => moisOrdre.indexOf(a.mois) - moisOrdre.indexOf(b.mois));

  const labels = stats.map(s => s.mois);
  const abonnements = stats.map(s => s.totalAbonnements);
  const resiliations = stats.map(s => s.totalResiliations);
  const abonnes = stats.map(s => s.totalAbonnes);

  this.setChartData(labels, abonnements, resiliations, abonnes);
}


  private setChartData(labels: string[], abonnements: number[], resiliations: number[], abonnes: number[]) {
    this.data = {
      labels,
      datasets: [
        {
          label: 'Abonnement',
          data: abonnements,
          fill: false,
          tension: 0.4,
          borderColor: '#ff0000'
        },
        {
          label: 'Résiliation',
          data: resiliations,
          fill: false,
          borderDash: [5, 5],
          tension: 0.4,
          borderColor: '#ffd700'
        },
        {
          label: 'Abonné',
          data: abonnes,
          fill: true,
          borderColor: '#0b8c50',
          tension: 0.4,
          backgroundColor: 'rgba(107, 114, 128, 0.2)'
        }
      ]
    };
  }

  private initEmptyChart() {
    this.setChartData([], [], [], []);
  }
}
