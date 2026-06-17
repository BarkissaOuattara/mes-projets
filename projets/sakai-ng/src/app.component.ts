import { Component, inject, signal } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ExploitationService } from './app/share/exploitation/exploitation.service';
import { Exploitation } from './app/share/exploitation/exploitation.model';
import { TypeBranch } from './app/share/typebranch/typebranch.model';
import { TypeBranchService } from './app/share/typebranch/typebranch.service';
import { ChartModule } from 'primeng/chart';
import { ChartLineStyle } from './app/pages/dashboard/components/chartlinestyle';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterModule,  ChartModule],
    template: `<router-outlet/>`
})
export class AppComponent {

    readonly exploitationService = inject(ExploitationService);
    readonly typebranchService = inject(TypeBranchService);
    exploitation = signal<Exploitation[]>([]);
    typebranch = signal<TypeBranch[]>([]);


    constructor() {
        this.exploitationService.getAllExploitation()
        this.typebranchService.getAllTypeBranch()
        //this.init();
    
    }
        async init() {
            this.exploitation.set(
                await this.exploitationService.getAllExploitation()
            );
}
}
