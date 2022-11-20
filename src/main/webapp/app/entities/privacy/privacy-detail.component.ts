import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrivacy } from 'app/shared/model/privacy.model';

@Component({
    selector: 'jhi-privacy-detail',
    templateUrl: './privacy-detail.component.html'
})
export class PrivacyDetailComponent implements OnInit {
    privacy: IPrivacy;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ privacy }) => {
            this.privacy = privacy;
        });
    }

    previousState() {
        window.history.back();
    }
}
