import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from './location.service';
import { INinoUser } from 'app/shared/model/nino-user.model';
import { NinoUserService } from 'app/entities/nino-user';
import { IStop } from 'app/shared/model/stop.model';
import { StopService } from 'app/entities/stop';

@Component({
    selector: 'jhi-location-update',
    templateUrl: './location-update.component.html'
})
export class LocationUpdateComponent implements OnInit {
    private _location: ILocation;
    isSaving: boolean;

    ninousers: INinoUser[];

    stops: IStop[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private locationService: LocationService,
        private ninoUserService: NinoUserService,
        private stopService: StopService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ location }) => {
            this.location = location;
        });
        this.ninoUserService.query().subscribe(
            (res: HttpResponse<INinoUser[]>) => {
                this.ninousers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.stopService.query().subscribe(
            (res: HttpResponse<IStop[]>) => {
                this.stops = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.location.id !== undefined) {
            this.subscribeToSaveResponse(this.locationService.update(this.location));
        } else {
            this.subscribeToSaveResponse(this.locationService.create(this.location));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILocation>>) {
        result.subscribe((res: HttpResponse<ILocation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackStopById(index: number, item: IStop) {
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
    get location() {
        return this._location;
    }

    set location(location: ILocation) {
        this._location = location;
    }
}
