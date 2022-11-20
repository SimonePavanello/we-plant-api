import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { EventAndLocation } from 'app/shared/model/event-and-location.model';
import { EventAndLocationService } from './event-and-location.service';
import { EventAndLocationComponent } from './event-and-location.component';
import { EventAndLocationDetailComponent } from './event-and-location-detail.component';
import { EventAndLocationUpdateComponent } from './event-and-location-update.component';
import { EventAndLocationDeletePopupComponent } from './event-and-location-delete-dialog.component';
import { IEventAndLocation } from 'app/shared/model/event-and-location.model';

@Injectable({ providedIn: 'root' })
export class EventAndLocationResolve implements Resolve<IEventAndLocation> {
    constructor(private service: EventAndLocationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((eventAndLocation: HttpResponse<EventAndLocation>) => eventAndLocation.body));
        }
        return of(new EventAndLocation());
    }
}

export const eventAndLocationRoute: Routes = [
    {
        path: 'event-and-location',
        component: EventAndLocationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EventAndLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'event-and-location/:id/view',
        component: EventAndLocationDetailComponent,
        resolve: {
            eventAndLocation: EventAndLocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EventAndLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'event-and-location/new',
        component: EventAndLocationUpdateComponent,
        resolve: {
            eventAndLocation: EventAndLocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EventAndLocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'event-and-location/:id/edit',
        component: EventAndLocationUpdateComponent,
        resolve: {
            eventAndLocation: EventAndLocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EventAndLocations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const eventAndLocationPopupRoute: Routes = [
    {
        path: 'event-and-location/:id/delete',
        component: EventAndLocationDeletePopupComponent,
        resolve: {
            eventAndLocation: EventAndLocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EventAndLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
