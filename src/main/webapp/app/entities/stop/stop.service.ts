import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStop } from 'app/shared/model/stop.model';

type EntityResponseType = HttpResponse<IStop>;
type EntityArrayResponseType = HttpResponse<IStop[]>;

@Injectable({ providedIn: 'root' })
export class StopService {
    private resourceUrl = SERVER_API_URL + 'api/stops';

    constructor(private http: HttpClient) {}

    create(stop: IStop): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(stop);
        return this.http
            .post<IStop>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(stop: IStop): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(stop);
        return this.http
            .put<IStop>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IStop>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStop[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(stop: IStop): IStop {
        const copy: IStop = Object.assign({}, stop, {
            startTime: stop.startTime != null && stop.startTime.isValid() ? stop.startTime.toJSON() : null,
            endTime: stop.endTime != null && stop.endTime.isValid() ? stop.endTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.startTime = res.body.startTime != null ? moment(res.body.startTime) : null;
        res.body.endTime = res.body.endTime != null ? moment(res.body.endTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((stop: IStop) => {
            stop.startTime = stop.startTime != null ? moment(stop.startTime) : null;
            stop.endTime = stop.endTime != null ? moment(stop.endTime) : null;
        });
        return res;
    }
}
