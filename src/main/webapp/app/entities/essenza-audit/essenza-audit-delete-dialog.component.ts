import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEssenzaAudit } from 'app/shared/model/essenza-audit.model';
import { EssenzaAuditService } from './essenza-audit.service';

@Component({
    selector: 'jhi-essenza-audit-delete-dialog',
    templateUrl: './essenza-audit-delete-dialog.component.html'
})
export class EssenzaAuditDeleteDialogComponent {
    essenzaAudit: IEssenzaAudit;

    constructor(
        private essenzaAuditService: EssenzaAuditService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.essenzaAuditService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'essenzaAuditListModification',
                content: 'Deleted an essenzaAudit'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-essenza-audit-delete-popup',
    template: ''
})
export class EssenzaAuditDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ essenzaAudit }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EssenzaAuditDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.essenzaAudit = essenzaAudit;
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
