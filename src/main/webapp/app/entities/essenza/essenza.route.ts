import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Essenza } from 'app/shared/model/essenza.model';
import { EssenzaService } from './essenza.service';
import { EssenzaComponent } from './essenza.component';
import { EssenzaDetailComponent } from './essenza-detail.component';
import { EssenzaUpdateComponent } from './essenza-update.component';
import { EssenzaDeletePopupComponent } from './essenza-delete-dialog.component';
import { IEssenza } from 'app/shared/model/essenza.model';

@Injectable({ providedIn: 'root' })
export class EssenzaResolve implements Resolve<IEssenza> {
    constructor(private service: EssenzaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((essenza: HttpResponse<Essenza>) => essenza.body));
        }
        return of(new Essenza());
    }
}

export const essenzaRoute: Routes = [
    {
        path: 'essenza',
        component: EssenzaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Essenzas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'essenza/:id/view',
        component: EssenzaDetailComponent,
        resolve: {
            essenza: EssenzaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Essenzas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'essenza/new',
        component: EssenzaUpdateComponent,
        resolve: {
            essenza: EssenzaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Essenzas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'essenza/:id/edit',
        component: EssenzaUpdateComponent,
        resolve: {
            essenza: EssenzaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Essenzas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const essenzaPopupRoute: Routes = [
    {
        path: 'essenza/:id/delete',
        component: EssenzaDeletePopupComponent,
        resolve: {
            essenza: EssenzaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Essenzas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
