import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPintor, Pintor } from 'app/shared/model/pintor.model';
import { PintorService } from './pintor.service';
import { PintorComponent } from './pintor.component';
import { PintorDetailComponent } from './pintor-detail.component';
import { PintorUpdateComponent } from './pintor-update.component';

@Injectable({ providedIn: 'root' })
export class PintorResolve implements Resolve<IPintor> {
  constructor(private service: PintorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPintor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pintor: HttpResponse<Pintor>) => {
          if (pintor.body) {
            return of(pintor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Pintor());
  }
}

export const pintorRoute: Routes = [
  {
    path: '',
    component: PintorComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Pintors',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PintorDetailComponent,
    resolve: {
      pintor: PintorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Pintors',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PintorUpdateComponent,
    resolve: {
      pintor: PintorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Pintors',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PintorUpdateComponent,
    resolve: {
      pintor: PintorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Pintors',
    },
    canActivate: [UserRouteAccessService],
  },
];
