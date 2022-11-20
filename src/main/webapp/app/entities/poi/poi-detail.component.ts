import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPoi } from 'app/shared/model/poi.model';

@Component({
    selector: 'jhi-poi-detail',
    templateUrl: './poi-detail.component.html'
})
export class PoiDetailComponent implements OnInit {
    poi: IPoi;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ poi }) => {
            this.poi = poi;
        });
    }

    previousState() {
        window.history.back();
    }
}
