import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlberoVisit } from 'app/shared/model/albero-visit.model';

@Component({
    selector: 'jhi-albero-visit-detail',
    templateUrl: './albero-visit-detail.component.html'
})
export class AlberoVisitDetailComponent implements OnInit {
    alberoVisit: IAlberoVisit;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ alberoVisit }) => {
            this.alberoVisit = alberoVisit;
        });
    }

    previousState() {
        window.history.back();
    }
}
