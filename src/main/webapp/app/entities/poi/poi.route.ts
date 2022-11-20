import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Poi } from 'app/shared/model/poi.model';
import { PoiService } from './poi.service';
import { PoiComponent } from './poi.component';
import { PoiDetailComponent } from './poi-detail.component';
import { PoiUpdateComponent } from './poi-update.component';
import { PoiDeletePopupComponent } from './poi-delete-dialog.component';
import { IPoi } from 'app/shared/model/poi.model';

@Injectable({ providedIn: 'root' })
export class PoiResolve implements Resolve<IPoi> {
    constructor(private service: PoiService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((poi: HttpResponse<Poi>) => poi.body));
        }
        return of(new Poi());
    }
}

export const poiRoute: Routes = [
    {
        path: 'poi',
        component: PoiComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pois'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'poi/:id/view',
        component: PoiDetailComponent,
        resolve: {
            poi: PoiResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pois'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'poi/new',
        component: PoiUpdateComponent,
        resolve: {
            poi: PoiResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pois'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'poi/:id/edit',
        component: PoiUpdateComponent,
        resolve: {
            poi: PoiResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pois'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const poiPopupRoute: Routes = [
    {
        path: 'poi/:id/delete',
        component: PoiDeletePopupComponent,
        resolve: {
            poi: PoiResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pois'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
