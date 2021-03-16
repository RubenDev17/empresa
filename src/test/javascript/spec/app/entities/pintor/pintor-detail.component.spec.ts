import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmpresaTestModule } from '../../../test.module';
import { PintorDetailComponent } from 'app/entities/pintor/pintor-detail.component';
import { Pintor } from 'app/shared/model/pintor.model';

describe('Component Tests', () => {
  describe('Pintor Management Detail Component', () => {
    let comp: PintorDetailComponent;
    let fixture: ComponentFixture<PintorDetailComponent>;
    const route = ({ data: of({ pintor: new Pintor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmpresaTestModule],
        declarations: [PintorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PintorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PintorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pintor on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pintor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
