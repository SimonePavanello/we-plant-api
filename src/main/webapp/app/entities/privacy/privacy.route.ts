import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Privacy } from 'app/shared/model/privacy.model';
import { PrivacyService } from './privacy.service';
import { PrivacyComponent } from './privacy.component';
import { PrivacyDetailComponent } from './privacy-detail.component';
import { PrivacyUpdateComponent } from './privacy-update.component';
import { PrivacyDeletePopupComponent } from './privacy-delete-dialog.component';
import { IPrivacy } from 'app/shared/model/privacy.model';

@Injectable({ providedIn: 'root' })
export class PrivacyResolve implements Resolve<IPrivacy> {
    constructor(private service: PrivacyService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((privacy: HttpResponse<Privacy>) => privacy.body));
        }
        return of(new Privacy());
    }
}

export const privacyRoute: Routes = [
    {
        path: 'privacy',
        component: PrivacyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Privacies'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'privacy/:id/view',
        component: PrivacyDetailComponent,
        resolve: {
            privacy: PrivacyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Privacies'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'privacy/new',
        component: PrivacyUpdateComponent,
        resolve: {
            privacy: PrivacyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Privacies'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'privacy/:id/edit',
        component: PrivacyUpdateComponent,
        resolve: {
            privacy: PrivacyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Privacies'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const privacyPopupRoute: Routes = [
    {
        path: 'privacy/:id/delete',
        component: PrivacyDeletePopupComponent,
        resolve: {
            privacy: PrivacyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Privacies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
