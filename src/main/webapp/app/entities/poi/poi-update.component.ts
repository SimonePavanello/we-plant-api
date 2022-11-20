import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPoi } from 'app/shared/model/poi.model';
import { PoiService } from './poi.service';
import { IEventAndLocation } from 'app/shared/model/event-and-location.model';
import { EventAndLocationService } from 'app/entities/event-and-location';

@Component({
    selector: 'jhi-poi-update',
    templateUrl: './poi-update.component.html'
})
export class PoiUpdateComponent implements OnInit {
    private _poi: IPoi;
    isSaving: boolean;

    eventandlocations: IEventAndLocation[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private poiService: PoiService,
        private eventAndLocationService: EventAndLocationService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ poi }) => {
            this.poi = poi;
        });
        this.eventAndLocationService.query().subscribe(
            (res: HttpResponse<IEventAndLocation[]>) => {
                this.eventandlocations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.poi.id !== undefined) {
            this.subscribeToSaveResponse(this.poiService.update(this.poi));
        } else {
            this.subscribeToSaveResponse(this.poiService.create(this.poi));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPoi>>) {
        result.subscribe((res: HttpResponse<IPoi>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackEventAndLocationById(index: number, item: IEventAndLocation) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get poi() {
        return this._poi;
    }

    set poi(poi: IPoi) {
        this._poi = poi;
    }
}
