import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INinoUser } from 'app/shared/model/nino-user.model';

@Component({
    selector: 'jhi-nino-user-detail',
    templateUrl: './nino-user-detail.component.html'
})
export class NinoUserDetailComponent implements OnInit {
    ninoUser: INinoUser;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ninoUser }) => {
            this.ninoUser = ninoUser;
        });
    }

    previousState() {
        window.history.back();
    }
}
