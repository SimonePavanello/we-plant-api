import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Visit } from 'app/shared/model/visit.model';
import { VisitService } from './visit.service';
import { VisitComponent } from './visit.component';
import { VisitDetailComponent } from './visit-detail.component';
import { VisitUpdateComponent } from './visit-update.component';
import { VisitDeletePopupComponent } from './visit-delete-dialog.component';
import { IVisit } from 'app/shared/model/visit.model';

@Injectable({ providedIn: 'root' })
export class VisitResolve implements Resolve<IVisit> {
    constructor(private service: VisitService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((visit: HttpResponse<Visit>) => visit.body));
        }
        return of(new Visit());
    }
}

export const visitRoute: Routes = [
    {
        path: 'visit',
        component: VisitComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Visits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'visit/:id/view',
        component: VisitDetailComponent,
        resolve: {
            visit: VisitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Visits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'visit/new',
        component: VisitUpdateComponent,
        resolve: {
            visit: VisitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Visits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'visit/:id/edit',
        component: VisitUpdateComponent,
        resolve: {
            visit: VisitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Visits'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const visitPopupRoute: Routes = [
    {
        path: 'visit/:id/delete',
        component: VisitDeletePopupComponent,
        resolve: {
            visit: VisitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Visits'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
