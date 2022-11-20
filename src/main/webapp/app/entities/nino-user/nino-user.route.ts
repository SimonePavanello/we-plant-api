import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { NinoUser } from 'app/shared/model/nino-user.model';
import { NinoUserService } from './nino-user.service';
import { NinoUserComponent } from './nino-user.component';
import { NinoUserDetailComponent } from './nino-user-detail.component';
import { NinoUserUpdateComponent } from './nino-user-update.component';
import { NinoUserDeletePopupComponent } from './nino-user-delete-dialog.component';
import { INinoUser } from 'app/shared/model/nino-user.model';

@Injectable({ providedIn: 'root' })
export class NinoUserResolve implements Resolve<INinoUser> {
    constructor(private service: NinoUserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((ninoUser: HttpResponse<NinoUser>) => ninoUser.body));
        }
        return of(new NinoUser());
    }
}

export const ninoUserRoute: Routes = [
    {
        path: 'nino-user',
        component: NinoUserComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NinoUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'nino-user/:id/view',
        component: NinoUserDetailComponent,
        resolve: {
            ninoUser: NinoUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NinoUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'nino-user/new',
        component: NinoUserUpdateComponent,
        resolve: {
            ninoUser: NinoUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NinoUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'nino-user/:id/edit',
        component: NinoUserUpdateComponent,
        resolve: {
            ninoUser: NinoUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NinoUsers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ninoUserPopupRoute: Routes = [
    {
        path: 'nino-user/:id/delete',
        component: NinoUserDeletePopupComponent,
        resolve: {
            ninoUser: NinoUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NinoUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
