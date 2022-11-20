import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IAlbero } from 'app/shared/model/albero.model';
import { AlberoService } from './albero.service';
import { IEssenza } from 'app/shared/model/essenza.model';
import { EssenzaService } from 'app/entities/essenza';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-albero-update',
    templateUrl: './albero-update.component.html'
})
export class AlberoUpdateComponent implements OnInit {
    private _albero: IAlbero;
    isSaving: boolean;

    essenzas: IEssenza[];

    users: IUser[];

    alberos: IAlbero[];
    dataImpianto: string;
    dataAbbattimento: string;
    dataUltimoAggiornamento: string;
    dataPrimaRilevazione: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private alberoService: AlberoService,
        private essenzaService: EssenzaService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ albero }) => {
            this.albero = albero;
        });
        this.essenzaService.query().subscribe(
            (res: HttpResponse<IEssenza[]>) => {
                this.essenzas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.alberoService.query().subscribe(
            (res: HttpResponse<IAlbero[]>) => {
                this.alberos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.albero.dataImpianto = moment(this.dataImpianto, DATE_TIME_FORMAT);
        this.albero.dataAbbattimento = moment(this.dataAbbattimento, DATE_TIME_FORMAT);
        this.albero.dataUltimoAggiornamento = moment(this.dataUltimoAggiornamento, DATE_TIME_FORMAT);
        this.albero.dataPrimaRilevazione = moment(this.dataPrimaRilevazione, DATE_TIME_FORMAT);
        if (this.albero.id !== undefined) {
            this.subscribeToSaveResponse(this.alberoService.update(this.albero));
        } else {
            this.subscribeToSaveResponse(this.alberoService.create(this.albero));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAlbero>>) {
        result.subscribe((res: HttpResponse<IAlbero>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEssenzaById(index: number, item: IEssenza) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackAlberoById(index: number, item: IAlbero) {
        return item.id;
    }
    get albero() {
        return this._albero;
    }

    set albero(albero: IAlbero) {
        this._albero = albero;
        this.dataImpianto = moment(albero.dataImpianto).format(DATE_TIME_FORMAT);
        this.dataAbbattimento = moment(albero.dataAbbattimento).format(DATE_TIME_FORMAT);
        this.dataUltimoAggiornamento = moment(albero.dataUltimoAggiornamento).format(DATE_TIME_FORMAT);
        this.dataPrimaRilevazione = moment(albero.dataPrimaRilevazione).format(DATE_TIME_FORMAT);
    }
}
