import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEventAndLocation } from 'app/shared/model/event-and-location.model';

type EntityResponseType = HttpResponse<IEventAndLocation>;
type EntityArrayResponseType = HttpResponse<IEventAndLocation[]>;

@Injectable({ providedIn: 'root' })
export class EventAndLocationService {
    private resourceUrl = SERVER_API_URL + 'api/event-and-locations';

    constructor(private http: HttpClient) {}

    create(eventAndLocation: IEventAndLocation): Observable<EntityResponseType> {
        return this.http.post<IEventAndLocation>(this.resourceUrl, eventAndLocation, { observe: 'response' });
    }

    update(eventAndLocation: IEventAndLocation): Observable<EntityResponseType> {
        return this.http.put<IEventAndLocation>(this.resourceUrl, eventAndLocation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEventAndLocation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEventAndLocation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
