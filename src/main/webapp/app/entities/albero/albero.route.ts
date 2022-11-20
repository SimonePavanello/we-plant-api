import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Albero } from 'app/shared/model/albero.model';
import { AlberoService } from './albero.service';
import { AlberoComponent } from './albero.component';
import { AlberoDetailComponent } from './albero-detail.component';
import { AlberoUpdateComponent } from './albero-update.component';
import { AlberoDeletePopupComponent } from './albero-delete-dialog.component';
import { IAlbero } from 'app/shared/model/albero.model';

@Injectable({ providedIn: 'root' })
export class AlberoResolve implements Resolve<IAlbero> {
    constructor(private service: AlberoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((albero: HttpResponse<Albero>) => albero.body));
        }
        return of(new Albero());
    }
}

export const alberoRoute: Routes = [
    {
        path: 'albero',
        component: AlberoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Alberos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'albero/:id/view',
        component: AlberoDetailComponent,
        resolve: {
            albero: AlberoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Alberos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'albero/new',
        component: AlberoUpdateComponent,
        resolve: {
            albero: AlberoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Alberos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'albero/:id/edit',
        component: AlberoUpdateComponent,
        resolve: {
            albero: AlberoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Alberos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const alberoPopupRoute: Routes = [
    {
        path: 'albero/:id/delete',
        component: AlberoDeletePopupComponent,
        resolve: {
            albero: AlberoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Alberos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
