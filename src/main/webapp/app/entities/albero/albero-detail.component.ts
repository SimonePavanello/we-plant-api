import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlbero } from 'app/shared/model/albero.model';

@Component({
    selector: 'jhi-albero-detail',
    templateUrl: './albero-detail.component.html'
})
export class AlberoDetailComponent implements OnInit {
    albero: IAlbero;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ albero }) => {
            this.albero = albero;
        });
    }

    previousState() {
        window.history.back();
    }
}
