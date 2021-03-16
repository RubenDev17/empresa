import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPintor } from 'app/shared/model/pintor.model';

@Component({
  selector: 'jhi-pintor-detail',
  templateUrl: './pintor-detail.component.html',
})
export class PintorDetailComponent implements OnInit {
  pintor: IPintor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pintor }) => (this.pintor = pintor));
  }

  previousState(): void {
    window.history.back();
  }
}
