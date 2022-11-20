import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Stop } from 'app/shared/model/stop.model';
import { StopService } from './stop.service';
import { StopComponent } from './stop.component';
import { StopDetailComponent } from './stop-detail.component';
import { StopUpdateComponent } from './stop-update.component';
import { StopDeletePopupComponent } from './stop-delete-dialog.component';
import { IStop } from 'app/shared/model/stop.model';

@Injectable({ providedIn: 'root' })
export class StopResolve implements Resolve<IStop> {
    constructor(private service: StopService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((stop: HttpResponse<Stop>) => stop.body));
        }
        return of(new Stop());
    }
}

export const stopRoute: Routes = [
    {
        path: 'stop',
        component: StopComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stops'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stop/:id/view',
        component: StopDetailComponent,
        resolve: {
            stop: StopResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stops'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stop/new',
        component: StopUpdateComponent,
        resolve: {
            stop: StopResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stops'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stop/:id/edit',
        component: StopUpdateComponent,
        resolve: {
            stop: StopResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stops'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stopPopupRoute: Routes = [
    {
        path: 'stop/:id/delete',
        component: StopDeletePopupComponent,
        resolve: {
            stop: StopResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stops'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
