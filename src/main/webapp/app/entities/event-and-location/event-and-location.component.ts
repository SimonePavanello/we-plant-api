import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEventAndLocation } from 'app/shared/model/event-and-location.model';
import { Principal } from 'app/core';
import { EventAndLocationService } from './event-and-location.service';

@Component({
    selector: 'jhi-event-and-location',
    templateUrl: './event-and-location.component.html'
})
export class EventAndLocationComponent implements OnInit, OnDestroy {
    eventAndLocations: IEventAndLocation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private eventAndLocationService: EventAndLocationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.eventAndLocationService.query().subscribe(
            (res: HttpResponse<IEventAndLocation[]>) => {
                this.eventAndLocations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEventAndLocations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEventAndLocation) {
        return item.id;
    }

    registerChangeInEventAndLocations() {
        this.eventSubscriber = this.eventManager.subscribe('eventAndLocationListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
