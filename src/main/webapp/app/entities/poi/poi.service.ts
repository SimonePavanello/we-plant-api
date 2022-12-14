import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPoi } from 'app/shared/model/poi.model';

type EntityResponseType = HttpResponse<IPoi>;
type EntityArrayResponseType = HttpResponse<IPoi[]>;

@Injectable({ providedIn: 'root' })
export class PoiService {
    private resourceUrl = SERVER_API_URL + 'api/pois';

    constructor(private http: HttpClient) {}

    create(poi: IPoi): Observable<EntityResponseType> {
        return this.http.post<IPoi>(this.resourceUrl, poi, { observe: 'response' });
    }

    update(poi: IPoi): Observable<EntityResponseType> {
        return this.http.put<IPoi>(this.resourceUrl, poi, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPoi>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPoi[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
