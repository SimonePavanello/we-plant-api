import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IStop } from 'app/shared/model/stop.model';
import { Principal } from 'app/core';
import { StopService } from './stop.service';

@Component({
    selector: 'jhi-stop',
    templateUrl: './stop.component.html'
})
export class StopComponent implements OnInit, OnDestroy {
    stops: IStop[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private stopService: StopService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.stopService.query().subscribe(
            (res: HttpResponse<IStop[]>) => {
                this.stops = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInStops();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IStop) {
        return item.id;
    }

    registerChangeInStops() {
        this.eventSubscriber = this.eventManager.subscribe('stopListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
