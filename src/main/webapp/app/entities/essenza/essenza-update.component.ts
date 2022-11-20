import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IEssenza } from 'app/shared/model/essenza.model';
import { EssenzaService } from './essenza.service';

@Component({
    selector: 'jhi-essenza-update',
    templateUrl: './essenza-update.component.html'
})
export class EssenzaUpdateComponent implements OnInit {
    private _essenza: IEssenza;
    isSaving: boolean;

    constructor(private essenzaService: EssenzaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ essenza }) => {
            this.essenza = essenza;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.essenza.id !== undefined) {
            this.subscribeToSaveResponse(this.essenzaService.update(this.essenza));
        } else {
            this.subscribeToSaveResponse(this.essenzaService.create(this.essenza));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEssenza>>) {
        result.subscribe((res: HttpResponse<IEssenza>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get essenza() {
        return this._essenza;
    }

    set essenza(essenza: IEssenza) {
        this._essenza = essenza;
    }
}
