import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAlbero } from 'app/shared/model/albero.model';
import { Principal } from 'app/core';
import { AlberoService } from './albero.service';

@Component({
    selector: 'jhi-albero',
    templateUrl: './albero.component.html'
})
export class AlberoComponent implements OnInit, OnDestroy {
    alberos: IAlbero[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private alberoService: AlberoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.alberoService.query().subscribe(
            (res: HttpResponse<IAlbero[]>) => {
                this.alberos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAlberos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAlbero) {
        return item.id;
    }

    registerChangeInAlberos() {
        this.eventSubscriber = this.eventManager.subscribe('alberoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
