import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEssenzaAudit } from 'app/shared/model/essenza-audit.model';
import { Principal } from 'app/core';
import { EssenzaAuditService } from './essenza-audit.service';

@Component({
    selector: 'jhi-essenza-audit',
    templateUrl: './essenza-audit.component.html'
})
export class EssenzaAuditComponent implements OnInit, OnDestroy {
    essenzaAudits: IEssenzaAudit[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private essenzaAuditService: EssenzaAuditService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.essenzaAuditService.query().subscribe(
            (res: HttpResponse<IEssenzaAudit[]>) => {
                this.essenzaAudits = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEssenzaAudits();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEssenzaAudit) {
        return item.id;
    }

    registerChangeInEssenzaAudits() {
        this.eventSubscriber = this.eventManager.subscribe('essenzaAuditListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
