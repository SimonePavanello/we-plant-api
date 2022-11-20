import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStop } from 'app/shared/model/stop.model';

@Component({
    selector: 'jhi-stop-detail',
    templateUrl: './stop-detail.component.html'
})
export class StopDetailComponent implements OnInit {
    stop: IStop;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stop }) => {
            this.stop = stop;
        });
    }

    previousState() {
        window.history.back();
    }
}
