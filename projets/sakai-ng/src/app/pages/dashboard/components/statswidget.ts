import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  standalone: true,
  selector: 'app-stats-widget',
  imports: [CommonModule],
  template: `
    <div class="col-span-12 lg:col-span-6 xl:col-span-3">
      <div class="card mb-0">
        <div class="flex justify-between mb-4">
          <div>
            <span class="block text-muted-color font-medium mb-4">Nombre d'abonnés</span>
            <div class="text-surface-900 dark:text-surface-0 font-medium text-xl">{{ stats.totalAbonnes }}</div>
          </div>
          <div class="flex items-center justify-center bg-blue-100 dark:bg-blue-400/10 rounded-border" style="width: 2.5rem; height: 2.5rem">
            <i class="pi pi-users text-blue-500 !text-xl"></i>
          </div>
        </div>
      </div>
    </div>

    <div class="col-span-12 lg:col-span-6 xl:col-span-3">
      <div class="card mb-0">
        <div class="flex justify-between mb-4">
          <div>
            <span class="block text-muted-color font-medium mb-4">Nombre d'exploitation</span>
            <div class="text-surface-900 dark:text-surface-0 font-medium text-xl">{{ stats.totalExploitations }}</div>
          </div>
          <div class="flex items-center justify-center bg-orange-100 dark:bg-orange-400/10 rounded-border" style="width: 2.5rem; height: 2.5rem">
            <i class="pi pi-building text-orange-500 !text-xl"></i>
          </div>
        </div>
      </div>
    </div>

    <div class="col-span-12 lg:col-span-6 xl:col-span-3">
      <div class="card mb-0">
        <div class="flex justify-between mb-4">
          <div>
            <span class="block text-muted-color font-medium mb-4">Nombre de branchement</span>
            <div class="text-surface-900 dark:text-surface-0 font-medium text-xl">{{ stats.totalBranchements }}</div>
          </div>
          <div class="flex items-center justify-center bg-cyan-100 dark:bg-cyan-400/10 rounded-border" style="width: 2.5rem; height: 2.5rem">
            <i class="pi pi-bolt text-cyan-500 !text-xl"></i>
          </div>
        </div>
      </div>
    </div>

    <div class="col-span-12 lg:col-span-6 xl:col-span-3">
      <div class="card mb-0">
        <div class="flex justify-between mb-4">
          <div>
            <span class="block text-muted-color font-medium mb-4">Nombre d'abonnement</span>
            <div class="text-surface-900 dark:text-surface-0 font-medium text-xl">{{ stats.totalAbonnements }}</div>
          </div>
          <div class="flex items-center justify-center bg-purple-100 dark:bg-purple-400/10 rounded-border" style="width: 2.5rem; height: 2.5rem">
            <i class="pi pi-file-edit text-purple-500 !text-xl"></i>
          </div>
        </div>
      </div>
    </div>
  `
})
export class StatsWidget implements OnInit {

  stats = {
    totalAbonnes: '0',
    totalAbonnements: '0',
    totalBranchements: '0',
    totalExploitations: '0'
  };

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get<any>('http://localhost:5000/dashboard').subscribe(data => {
      this.stats = data;
    });
  }
}
