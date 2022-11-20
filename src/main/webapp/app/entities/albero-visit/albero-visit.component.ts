import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAlberoVisit } from 'app/shared/model/albero-visit.model';
import { Principal } from 'app/core';
import { AlberoVisitService } from './albero-visit.service';

@Component({
    selector: 'jhi-albero-visit',
    templateUrl: './albero-visit.component.html'
})
export class AlberoVisitComponent implements OnInit, OnDestroy {
    alberoVisits: IAlberoVisit[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private alberoVisitService: AlberoVisitService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.alberoVisitService.query().subscribe(
            (res: HttpResponse<IAlberoVisit[]>) => {
                this.alberoVisits = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAlberoVisits();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAlberoVisit) {
        return item.id;
    }

    registerChangeInAlberoVisits() {
        this.eventSubscriber = this.eventManager.subscribe('alberoVisitListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
