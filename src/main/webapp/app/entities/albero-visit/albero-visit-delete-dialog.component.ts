import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlberoVisit } from 'app/shared/model/albero-visit.model';
import { AlberoVisitService } from './albero-visit.service';

@Component({
    selector: 'jhi-albero-visit-delete-dialog',
    templateUrl: './albero-visit-delete-dialog.component.html'
})
export class AlberoVisitDeleteDialogComponent {
    alberoVisit: IAlberoVisit;

    constructor(
        private alberoVisitService: AlberoVisitService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.alberoVisitService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'alberoVisitListModification',
                content: 'Deleted an alberoVisit'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-albero-visit-delete-popup',
    template: ''
})
export class AlberoVisitDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ alberoVisit }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AlberoVisitDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.alberoVisit = alberoVisit;
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
