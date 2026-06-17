import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LogService } from './log.service';
import { AppLogs, LogType } from './app-logs.model';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';


@Component({
  standalone: true,
  selector: 'app-logs',
  templateUrl: './log.component.html',
  imports: [
    TableModule,
    CommonModule,
    FormsModule,
    InputTextModule,
    TableModule,
    InputIconModule,
    IconFieldModule
]
})
export class LogComponent implements OnInit {
  logs: AppLogs[] = [];
  filteredLogs: AppLogs[] = [];
  searchQuery: string = '';
   isWorking: boolean = false;

  constructor(private logService: LogService) {}

  ngOnInit(): void {
    this.loadLogs();
  }

  loadLogs(): void {
    this.isWorking = true;
    this.logService.getAllLogs().subscribe(data => {
      this.logs = data;
      this.filteredLogs = data;
        this.isWorking = false;
    });
  }

  filterLogs(): void {
    const query = this.searchQuery.toLowerCase();
    this.filteredLogs = this.logs.filter(log =>
      log.logType.toLowerCase().includes(query) ||
      log.entityName.toLowerCase().includes(query) ||
      log.authorCode.toLowerCase().includes(query) ||
      log.codeObject.toLowerCase().includes(query) ||
      log.message.toLowerCase().includes(query)
    );
  }

  getColor(logType: LogType): string {
    switch (logType) {
      case LogType.CREATE: return 'text-green-600 font-bold';
      case LogType.UPDATE: return 'text-blue-600 font-bold';
      case LogType.DELETE: return 'text-red-600 font-bold';
      case LogType.LOGIN: return 'text-green-700';
      case LogType.LOGOUT: return 'text-yellow-700';
      case LogType.RESILIATION: return 'text-red-700 font-semibold';
      default: return 'text-gray-700';
    }
  }
}
