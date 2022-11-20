import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEssenzaAudit } from 'app/shared/model/essenza-audit.model';

type EntityResponseType = HttpResponse<IEssenzaAudit>;
type EntityArrayResponseType = HttpResponse<IEssenzaAudit[]>;

@Injectable({ providedIn: 'root' })
export class EssenzaAuditService {
    private resourceUrl = SERVER_API_URL + 'api/essenza-audits';

    constructor(private http: HttpClient) {}

    create(essenzaAudit: IEssenzaAudit): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(essenzaAudit);
        return this.http
            .post<IEssenzaAudit>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(essenzaAudit: IEssenzaAudit): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(essenzaAudit);
        return this.http
            .put<IEssenzaAudit>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IEssenzaAudit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEssenzaAudit[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(essenzaAudit: IEssenzaAudit): IEssenzaAudit {
        const copy: IEssenzaAudit = Object.assign({}, essenzaAudit, {
            dataUltimoAggiornamento:
                essenzaAudit.dataUltimoAggiornamento != null && essenzaAudit.dataUltimoAggiornamento.isValid()
                    ? essenzaAudit.dataUltimoAggiornamento.toJSON()
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dataUltimoAggiornamento = res.body.dataUltimoAggiornamento != null ? moment(res.body.dataUltimoAggiornamento) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((essenzaAudit: IEssenzaAudit) => {
            essenzaAudit.dataUltimoAggiornamento =
                essenzaAudit.dataUltimoAggiornamento != null ? moment(essenzaAudit.dataUltimoAggiornamento) : null;
        });
        return res;
    }
}
