import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INinoUser } from 'app/shared/model/nino-user.model';
import { NinoUserService } from './nino-user.service';

@Component({
    selector: 'jhi-nino-user-delete-dialog',
    templateUrl: './nino-user-delete-dialog.component.html'
})
export class NinoUserDeleteDialogComponent {
    ninoUser: INinoUser;

    constructor(private ninoUserService: NinoUserService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ninoUserService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'ninoUserListModification',
                content: 'Deleted an ninoUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-nino-user-delete-popup',
    template: ''
})
export class NinoUserDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ninoUser }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(NinoUserDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.ninoUser = ninoUser;
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
