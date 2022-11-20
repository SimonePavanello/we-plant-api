import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEventAndLocation } from 'app/shared/model/event-and-location.model';
import { EventAndLocationService } from './event-and-location.service';

@Component({
    selector: 'jhi-event-and-location-delete-dialog',
    templateUrl: './event-and-location-delete-dialog.component.html'
})
export class EventAndLocationDeleteDialogComponent {
    eventAndLocation: IEventAndLocation;

    constructor(
        private eventAndLocationService: EventAndLocationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.eventAndLocationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'eventAndLocationListModification',
                content: 'Deleted an eventAndLocation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-event-and-location-delete-popup',
    template: ''
})
export class EventAndLocationDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ eventAndLocation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EventAndLocationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.eventAndLocation = eventAndLocation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
