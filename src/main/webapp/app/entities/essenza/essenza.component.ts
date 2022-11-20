import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEssenza } from 'app/shared/model/essenza.model';
import { Principal } from 'app/core';
import { EssenzaService } from './essenza.service';

@Component({
    selector: 'jhi-essenza',
    templateUrl: './essenza.component.html'
})
export class EssenzaComponent implements OnInit, OnDestroy {
    essenzas: IEssenza[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private essenzaService: EssenzaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.essenzaService.query().subscribe(
            (res: HttpResponse<IEssenza[]>) => {
                this.essenzas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEssenzas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEssenza) {
        return item.id;
    }

    registerChangeInEssenzas() {
        this.eventSubscriber = this.eventManager.subscribe('essenzaListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
