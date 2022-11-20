import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IVisit } from 'app/shared/model/visit.model';
import { Principal } from 'app/core';
import { VisitService } from './visit.service';

@Component({
    selector: 'jhi-visit',
    templateUrl: './visit.component.html'
})
export class VisitComponent implements OnInit, OnDestroy {
    visits: IVisit[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private visitService: VisitService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.visitService.query().subscribe(
            (res: HttpResponse<IVisit[]>) => {
                this.visits = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInVisits();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IVisit) {
        return item.id;
    }

    registerChangeInVisits() {
        this.eventSubscriber = this.eventManager.subscribe('visitListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
