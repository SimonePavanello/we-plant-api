import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEssenzaAudit } from 'app/shared/model/essenza-audit.model';

@Component({
    selector: 'jhi-essenza-audit-detail',
    templateUrl: './essenza-audit-detail.component.html'
})
export class EssenzaAuditDetailComponent implements OnInit {
    essenzaAudit: IEssenzaAudit;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ essenzaAudit }) => {
            this.essenzaAudit = essenzaAudit;
        });
    }

    previousState() {
        window.history.back();
    }
}
