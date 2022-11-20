import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPrivacy } from 'app/shared/model/privacy.model';

type EntityResponseType = HttpResponse<IPrivacy>;
type EntityArrayResponseType = HttpResponse<IPrivacy[]>;

@Injectable({ providedIn: 'root' })
export class PrivacyService {
    private resourceUrl = SERVER_API_URL + 'api/privacies';

    constructor(private http: HttpClient) {}

    create(privacy: IPrivacy): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(privacy);
        return this.http
            .post<IPrivacy>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(privacy: IPrivacy): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(privacy);
        return this.http
            .put<IPrivacy>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPrivacy>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPrivacy[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(privacy: IPrivacy): IPrivacy {
        const copy: IPrivacy = Object.assign({}, privacy, {
            time: privacy.time != null && privacy.time.isValid() ? privacy.time.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.time = res.body.time != null ? moment(res.body.time) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((privacy: IPrivacy) => {
            privacy.time = privacy.time != null ? moment(privacy.time) : null;
        });
        return res;
    }
}
