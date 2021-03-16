import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EmpresaTestModule } from '../../../test.module';
import { PintorUpdateComponent } from 'app/entities/pintor/pintor-update.component';
import { PintorService } from 'app/entities/pintor/pintor.service';
import { Pintor } from 'app/shared/model/pintor.model';

describe('Component Tests', () => {
  describe('Pintor Management Update Component', () => {
    let comp: PintorUpdateComponent;
    let fixture: ComponentFixture<PintorUpdateComponent>;
    let service: PintorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmpresaTestModule],
        declarations: [PintorUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PintorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PintorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PintorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pintor(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pintor();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
