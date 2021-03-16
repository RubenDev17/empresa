import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPintor, Pintor } from 'app/shared/model/pintor.model';
import { PintorService } from './pintor.service';

@Component({
  selector: 'jhi-pintor-update',
  templateUrl: './pintor-update.component.html',
})
export class PintorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.minLength(0), Validators.maxLength(20)]],
    apellidos: [null, [Validators.minLength(0), Validators.maxLength(20)]],
    sueldo: [],
    experiencia: [],
  });

  constructor(protected pintorService: PintorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pintor }) => {
      this.updateForm(pintor);
    });
  }

  updateForm(pintor: IPintor): void {
    this.editForm.patchValue({
      id: pintor.id,
      nombre: pintor.nombre,
      apellidos: pintor.apellidos,
      sueldo: pintor.sueldo,
      experiencia: pintor.experiencia,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pintor = this.createFromForm();
    if (pintor.id !== undefined) {
      this.subscribeToSaveResponse(this.pintorService.update(pintor));
    } else {
      this.subscribeToSaveResponse(this.pintorService.create(pintor));
    }
  }

  private createFromForm(): IPintor {
    return {
      ...new Pintor(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      apellidos: this.editForm.get(['apellidos'])!.value,
      sueldo: this.editForm.get(['sueldo'])!.value,
      experiencia: this.editForm.get(['experiencia'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPintor>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
