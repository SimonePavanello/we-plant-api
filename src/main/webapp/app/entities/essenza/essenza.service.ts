import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEssenza } from 'app/shared/model/essenza.model';

type EntityResponseType = HttpResponse<IEssenza>;
type EntityArrayResponseType = HttpResponse<IEssenza[]>;

@Injectable({ providedIn: 'root' })
export class EssenzaService {
    private resourceUrl = SERVER_API_URL + 'api/essenzas';

    constructor(private http: HttpClient) {}

    create(essenza: IEssenza): Observable<EntityResponseType> {
        return this.http.post<IEssenza>(this.resourceUrl, essenza, { observe: 'response' });
    }

    update(essenza: IEssenza): Observable<EntityResponseType> {
        return this.http.put<IEssenza>(this.resourceUrl, essenza, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEssenza>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEssenza[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
