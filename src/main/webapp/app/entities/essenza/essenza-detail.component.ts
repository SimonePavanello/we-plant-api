import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEssenza } from 'app/shared/model/essenza.model';

@Component({
    selector: 'jhi-essenza-detail',
    templateUrl: './essenza-detail.component.html'
})
export class EssenzaDetailComponent implements OnInit {
    essenza: IEssenza;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ essenza }) => {
            this.essenza = essenza;
        });
    }

    previousState() {
        window.history.back();
    }
}
