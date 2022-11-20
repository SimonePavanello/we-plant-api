import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventAndLocation } from 'app/shared/model/event-and-location.model';

@Component({
    selector: 'jhi-event-and-location-detail',
    templateUrl: './event-and-location-detail.component.html'
})
export class EventAndLocationDetailComponent implements OnInit {
    eventAndLocation: IEventAndLocation;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ eventAndLocation }) => {
            this.eventAndLocation = eventAndLocation;
        });
    }

    previousState() {
        window.history.back();
    }
}
