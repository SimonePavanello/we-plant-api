import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAlbero } from 'app/shared/model/albero.model';

type EntityResponseType = HttpResponse<IAlbero>;
type EntityArrayResponseType = HttpResponse<IAlbero[]>;

@Injectable({ providedIn: 'root' })
export class AlberoService {
    private resourceUrl = SERVER_API_URL + 'api/alberos';

    constructor(private http: HttpClient) {}

    create(albero: IAlbero): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(albero);
        return this.http
            .post<IAlbero>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(albero: IAlbero): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(albero);
        return this.http
            .put<IAlbero>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAlbero>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAlbero[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(albero: IAlbero): IAlbero {
        const copy: IAlbero = Object.assign({}, albero, {
            dataImpianto: albero.dataImpianto != null && albero.dataImpianto.isValid() ? albero.dataImpianto.toJSON() : null,
            dataAbbattimento:
                albero.dataAbbattimento != null && albero.dataAbbattimento.isValid() ? albero.dataAbbattimento.toJSON() : null,
            dataUltimoAggiornamento:
                albero.dataUltimoAggiornamento != null && albero.dataUltimoAggiornamento.isValid()
                    ? albero.dataUltimoAggiornamento.toJSON()
                    : null,
            dataPrimaRilevazione:
                albero.dataPrimaRilevazione != null && albero.dataPrimaRilevazione.isValid() ? albero.dataPrimaRilevazione.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dataImpianto = res.body.dataImpianto != null ? moment(res.body.dataImpianto) : null;
        res.body.dataAbbattimento = res.body.dataAbbattimento != null ? moment(res.body.dataAbbattimento) : null;
        res.body.dataUltimoAggiornamento = res.body.dataUltimoAggiornamento != null ? moment(res.body.dataUltimoAggiornamento) : null;
        res.body.dataPrimaRilevazione = res.body.dataPrimaRilevazione != null ? moment(res.body.dataPrimaRilevazione) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((albero: IAlbero) => {
            albero.dataImpianto = albero.dataImpianto != null ? moment(albero.dataImpianto) : null;
            albero.dataAbbattimento = albero.dataAbbattimento != null ? moment(albero.dataAbbattimento) : null;
            albero.dataUltimoAggiornamento = albero.dataUltimoAggiornamento != null ? moment(albero.dataUltimoAggiornamento) : null;
            albero.dataPrimaRilevazione = albero.dataPrimaRilevazione != null ? moment(albero.dataPrimaRilevazione) : null;
        });
        return res;
    }
}
