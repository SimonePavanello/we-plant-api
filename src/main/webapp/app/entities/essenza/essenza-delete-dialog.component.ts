import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEssenza } from 'app/shared/model/essenza.model';
import { EssenzaService } from './essenza.service';

@Component({
    selector: 'jhi-essenza-delete-dialog',
    templateUrl: './essenza-delete-dialog.component.html'
})
export class EssenzaDeleteDialogComponent {
    essenza: IEssenza;

    constructor(private essenzaService: EssenzaService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.essenzaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'essenzaListModification',
                content: 'Deleted an essenza'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-essenza-delete-popup',
    template: ''
})
export class EssenzaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ essenza }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EssenzaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.essenza = essenza;
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
