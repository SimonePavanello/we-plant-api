import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { EssenzaAudit } from 'app/shared/model/essenza-audit.model';
import { EssenzaAuditService } from './essenza-audit.service';
import { EssenzaAuditComponent } from './essenza-audit.component';
import { EssenzaAuditDetailComponent } from './essenza-audit-detail.component';
import { EssenzaAuditUpdateComponent } from './essenza-audit-update.component';
import { EssenzaAuditDeletePopupComponent } from './essenza-audit-delete-dialog.component';
import { IEssenzaAudit } from 'app/shared/model/essenza-audit.model';

@Injectable({ providedIn: 'root' })
export class EssenzaAuditResolve implements Resolve<IEssenzaAudit> {
    constructor(private service: EssenzaAuditService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((essenzaAudit: HttpResponse<EssenzaAudit>) => essenzaAudit.body));
        }
        return of(new EssenzaAudit());
    }
}

export const essenzaAuditRoute: Routes = [
    {
        path: 'essenza-audit',
        component: EssenzaAuditComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EssenzaAudits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'essenza-audit/:id/view',
        component: EssenzaAuditDetailComponent,
        resolve: {
            essenzaAudit: EssenzaAuditResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EssenzaAudits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'essenza-audit/new',
        component: EssenzaAuditUpdateComponent,
        resolve: {
            essenzaAudit: EssenzaAuditResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EssenzaAudits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'essenza-audit/:id/edit',
        component: EssenzaAuditUpdateComponent,
        resolve: {
            essenzaAudit: EssenzaAuditResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EssenzaAudits'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const essenzaAuditPopupRoute: Routes = [
    {
        path: 'essenza-audit/:id/delete',
        component: EssenzaAuditDeletePopupComponent,
        resolve: {
            essenzaAudit: EssenzaAuditResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EssenzaAudits'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
