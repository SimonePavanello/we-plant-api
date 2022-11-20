import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AlberoVisit } from 'app/shared/model/albero-visit.model';
import { AlberoVisitService } from './albero-visit.service';
import { AlberoVisitComponent } from './albero-visit.component';
import { AlberoVisitDetailComponent } from './albero-visit-detail.component';
import { AlberoVisitUpdateComponent } from './albero-visit-update.component';
import { AlberoVisitDeletePopupComponent } from './albero-visit-delete-dialog.component';
import { IAlberoVisit } from 'app/shared/model/albero-visit.model';

@Injectable({ providedIn: 'root' })
export class AlberoVisitResolve implements Resolve<IAlberoVisit> {
    constructor(private service: AlberoVisitService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((alberoVisit: HttpResponse<AlberoVisit>) => alberoVisit.body));
        }
        return of(new AlberoVisit());
    }
}

export const alberoVisitRoute: Routes = [
    {
        path: 'albero-visit',
        component: AlberoVisitComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AlberoVisits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'albero-visit/:id/view',
        component: AlberoVisitDetailComponent,
        resolve: {
            alberoVisit: AlberoVisitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AlberoVisits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'albero-visit/new',
        component: AlberoVisitUpdateComponent,
        resolve: {
            alberoVisit: AlberoVisitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AlberoVisits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'albero-visit/:id/edit',
        component: AlberoVisitUpdateComponent,
        resolve: {
            alberoVisit: AlberoVisitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AlberoVisits'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const alberoVisitPopupRoute: Routes = [
    {
        path: 'albero-visit/:id/delete',
        component: AlberoVisitDeletePopupComponent,
        resolve: {
            alberoVisit: AlberoVisitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AlberoVisits'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
