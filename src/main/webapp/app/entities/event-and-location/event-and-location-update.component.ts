import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IEventAndLocation } from 'app/shared/model/event-and-location.model';
import { EventAndLocationService } from './event-and-location.service';
import { INinoUser } from 'app/shared/model/nino-user.model';
import { NinoUserService } from 'app/entities/nino-user';
import { IPoi } from 'app/shared/model/poi.model';
import { PoiService } from 'app/entities/poi';

@Component({
    selector: 'jhi-event-and-location-update',
    templateUrl: './event-and-location-update.component.html'
})
export class EventAndLocationUpdateComponent implements OnInit {
    private _eventAndLocation: IEventAndLocation;
    isSaving: boolean;

    ninousers: INinoUser[];

    pois: IPoi[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private eventAndLocationService: EventAndLocationService,
        private ninoUserService: NinoUserService,
        private poiService: PoiService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ eventAndLocation }) => {
            this.eventAndLocation = eventAndLocation;
        });
        this.ninoUserService.query().subscribe(
            (res: HttpResponse<INinoUser[]>) => {
                this.ninousers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.poiService.query().subscribe(
            (res: HttpResponse<IPoi[]>) => {
                this.pois = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.eventAndLocation.id !== undefined) {
            this.subscribeToSaveResponse(this.eventAndLocationService.update(this.eventAndLocation));
        } else {
            this.subscribeToSaveResponse(this.eventAndLocationService.create(this.eventAndLocation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEventAndLocation>>) {
        result.subscribe((res: HttpResponse<IEventAndLocation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackNinoUserById(index: number, item: INinoUser) {
        return item.id;
    }

    trackPoiById(index: number, item: IPoi) {
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
    get eventAndLocation() {
        return this._eventAndLocation;
    }

    set eventAndLocation(eventAndLocation: IEventAndLocation) {
        this._eventAndLocation = eventAndLocation;
    }
}
