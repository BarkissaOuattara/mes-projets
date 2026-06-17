import { Component } from '@angular/core';
import { NotificationsWidget } from './components/notificationswidget';
import { StatsWidget } from './components/statswidget';
import { BestSellingWidget } from './components/bestsellingwidget';
import { CardModule } from 'primeng/card';
import { RecentAbonnementsWidgetComponent } from "./components/recentabonnementswidget";
import { ChartLineStyle } from './components/chartlinestyle';
import { ChartModule } from 'primeng/chart';


@Component({
    selector: 'app-dashboard',
    imports: [StatsWidget, ChartLineStyle, CardModule, RecentAbonnementsWidgetComponent, ChartModule],
    template: `
        <div class="grid grid-cols-12 gap-8">
            <app-stats-widget class="contents" />
            <div class="col-span-12 xl:col-span-6">
            <app-recent-abonnements-widget />
            </div>
            <div class="col-span-12 xl:col-span-6">
                <app-chart-line-style/>
            </div>
        </div>
    `
})
export class Dashboard {}
