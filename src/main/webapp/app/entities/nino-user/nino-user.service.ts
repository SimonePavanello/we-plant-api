import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INinoUser } from 'app/shared/model/nino-user.model';

type EntityResponseType = HttpResponse<INinoUser>;
type EntityArrayResponseType = HttpResponse<INinoUser[]>;

@Injectable({ providedIn: 'root' })
export class NinoUserService {
    private resourceUrl = SERVER_API_URL + 'api/nino-users';

    constructor(private http: HttpClient) {}

    create(ninoUser: INinoUser): Observable<EntityResponseType> {
        return this.http.post<INinoUser>(this.resourceUrl, ninoUser, { observe: 'response' });
    }

    update(ninoUser: INinoUser): Observable<EntityResponseType> {
        return this.http.put<INinoUser>(this.resourceUrl, ninoUser, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<INinoUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<INinoUser[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
