import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAlberoVisit } from 'app/shared/model/albero-visit.model';

type EntityResponseType = HttpResponse<IAlberoVisit>;
type EntityArrayResponseType = HttpResponse<IAlberoVisit[]>;

@Injectable({ providedIn: 'root' })
export class AlberoVisitService {
    private resourceUrl = SERVER_API_URL + 'api/albero-visits';

    constructor(private http: HttpClient) {}

    create(alberoVisit: IAlberoVisit): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(alberoVisit);
        return this.http
            .post<IAlberoVisit>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(alberoVisit: IAlberoVisit): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(alberoVisit);
        return this.http
            .put<IAlberoVisit>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAlberoVisit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAlberoVisit[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(alberoVisit: IAlberoVisit): IAlberoVisit {
        const copy: IAlberoVisit = Object.assign({}, alberoVisit, {
            visitTime: alberoVisit.visitTime != null && alberoVisit.visitTime.isValid() ? alberoVisit.visitTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.visitTime = res.body.visitTime != null ? moment(res.body.visitTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((alberoVisit: IAlberoVisit) => {
            alberoVisit.visitTime = alberoVisit.visitTime != null ? moment(alberoVisit.visitTime) : null;
        });
        return res;
    }
}
