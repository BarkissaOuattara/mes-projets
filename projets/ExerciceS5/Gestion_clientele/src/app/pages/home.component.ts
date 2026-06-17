import { Component, OnInit } from "@angular/core";
import { ConfirmationService, MessageService } from "primeng/api";




@Component({
    selector: 'classe',
    templateUrl: './home.component.html',
    imports: [
    ],
    providers: [
        ConfirmationService,
        MessageService,
        
    ]
})
export class HomeComponent implements OnInit {

    ngOnInit(): void { }

    
}