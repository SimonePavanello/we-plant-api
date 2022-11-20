import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVisit } from 'app/shared/model/visit.model';

type EntityResponseType = HttpResponse<IVisit>;
type EntityArrayResponseType = HttpResponse<IVisit[]>;

@Injectable({ providedIn: 'root' })
export class VisitService {
    private resourceUrl = SERVER_API_URL + 'api/visits';

    constructor(private http: HttpClient) {}

    create(visit: IVisit): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(visit);
        return this.http
            .post<IVisit>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(visit: IVisit): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(visit);
        return this.http
            .put<IVisit>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IVisit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IVisit[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(visit: IVisit): IVisit {
        const copy: IVisit = Object.assign({}, visit, {
            startTime: visit.startTime != null && visit.startTime.isValid() ? visit.startTime.toJSON() : null,
            createdDate: visit.createdDate != null && visit.createdDate.isValid() ? visit.createdDate.toJSON() : null,
            modifiedDate: visit.modifiedDate != null && visit.modifiedDate.isValid() ? visit.modifiedDate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.startTime = res.body.startTime != null ? moment(res.body.startTime) : null;
        res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
        res.body.modifiedDate = res.body.modifiedDate != null ? moment(res.body.modifiedDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((visit: IVisit) => {
            visit.startTime = visit.startTime != null ? moment(visit.startTime) : null;
            visit.createdDate = visit.createdDate != null ? moment(visit.createdDate) : null;
            visit.modifiedDate = visit.modifiedDate != null ? moment(visit.modifiedDate) : null;
        });
        return res;
    }
}
