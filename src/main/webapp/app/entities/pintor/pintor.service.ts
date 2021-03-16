import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IPintor } from 'app/shared/model/pintor.model';

type EntityResponseType = HttpResponse<IPintor>;
type EntityArrayResponseType = HttpResponse<IPintor[]>;

@Injectable({ providedIn: 'root' })
export class PintorService {
  public resourceUrl = SERVER_API_URL + 'api/pintors';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/pintors';

  constructor(protected http: HttpClient) {}

  create(pintor: IPintor): Observable<EntityResponseType> {
    return this.http.post<IPintor>(this.resourceUrl, pintor, { observe: 'response' });
  }

  update(pintor: IPintor): Observable<EntityResponseType> {
    return this.http.put<IPintor>(this.resourceUrl, pintor, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPintor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPintor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPintor[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
