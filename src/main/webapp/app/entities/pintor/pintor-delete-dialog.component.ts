import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPintor } from 'app/shared/model/pintor.model';
import { PintorService } from './pintor.service';

@Component({
  templateUrl: './pintor-delete-dialog.component.html',
})
export class PintorDeleteDialogComponent {
  pintor?: IPintor;

  constructor(protected pintorService: PintorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pintorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pintorListModification');
      this.activeModal.close();
    });
  }
}
