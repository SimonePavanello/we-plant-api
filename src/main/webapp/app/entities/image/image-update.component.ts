import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IImage } from 'app/shared/model/image.model';
import { ImageService } from './image.service';
import { IAlbero } from 'app/shared/model/albero.model';
import { AlberoService } from 'app/entities/albero';
import { IPoi } from 'app/shared/model/poi.model';
import { PoiService } from 'app/entities/poi';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-image-update',
    templateUrl: './image-update.component.html'
})
export class ImageUpdateComponent implements OnInit {
    private _image: IImage;
    isSaving: boolean;

    alberos: IAlbero[];

    pois: IPoi[];

    users: IUser[];
    createDate: string;
    modifiedDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private imageService: ImageService,
        private alberoService: AlberoService,
        private poiService: PoiService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ image }) => {
            this.image = image;
        });
        this.alberoService.query().subscribe(
            (res: HttpResponse<IAlbero[]>) => {
                this.alberos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.poiService.query().subscribe(
            (res: HttpResponse<IPoi[]>) => {
                this.pois = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.image.createDate = moment(this.createDate, DATE_TIME_FORMAT);
        this.image.modifiedDate = moment(this.modifiedDate, DATE_TIME_FORMAT);
        if (this.image.id !== undefined) {
            this.subscribeToSaveResponse(this.imageService.update(this.image));
        } else {
            this.subscribeToSaveResponse(this.imageService.create(this.image));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IImage>>) {
        result.subscribe((res: HttpResponse<IImage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAlberoById(index: number, item: IAlbero) {
        return item.id;
    }

    trackPoiById(index: number, item: IPoi) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
    get image() {
        return this._image;
    }

    set image(image: IImage) {
        this._image = image;
        this.createDate = moment(image.createDate).format(DATE_TIME_FORMAT);
        this.modifiedDate = moment(image.modifiedDate).format(DATE_TIME_FORMAT);
    }
}
