import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICarteBancaire, NewCarteBancaire } from '../carte-bancaire.model';

export type PartialUpdateCarteBancaire = Partial<ICarteBancaire> & Pick<ICarteBancaire, 'id'>;

type RestOf<T extends ICarteBancaire | NewCarteBancaire> = Omit<T, 'dateExpiration'> & {
  dateExpiration?: string | null;
};

export type RestCarteBancaire = RestOf<ICarteBancaire>;

export type NewRestCarteBancaire = RestOf<NewCarteBancaire>;

export type PartialUpdateRestCarteBancaire = RestOf<PartialUpdateCarteBancaire>;

export type EntityResponseType = HttpResponse<ICarteBancaire>;
export type EntityArrayResponseType = HttpResponse<ICarteBancaire[]>;

@Injectable({ providedIn: 'root' })
export class CarteBancaireService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/carte-bancaires');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(carteBancaire: NewCarteBancaire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carteBancaire);
    return this.http
      .post<RestCarteBancaire>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(carteBancaire: ICarteBancaire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carteBancaire);
    return this.http
      .put<RestCarteBancaire>(`${this.resourceUrl}/${this.getCarteBancaireIdentifier(carteBancaire)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(carteBancaire: PartialUpdateCarteBancaire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carteBancaire);
    return this.http
      .patch<RestCarteBancaire>(`${this.resourceUrl}/${this.getCarteBancaireIdentifier(carteBancaire)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCarteBancaire>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCarteBancaire[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCarteBancaireIdentifier(carteBancaire: Pick<ICarteBancaire, 'id'>): number {
    return carteBancaire.id;
  }

  compareCarteBancaire(o1: Pick<ICarteBancaire, 'id'> | null, o2: Pick<ICarteBancaire, 'id'> | null): boolean {
    return o1 && o2 ? this.getCarteBancaireIdentifier(o1) === this.getCarteBancaireIdentifier(o2) : o1 === o2;
  }

  addCarteBancaireToCollectionIfMissing<Type extends Pick<ICarteBancaire, 'id'>>(
    carteBancaireCollection: Type[],
    ...carteBancairesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const carteBancaires: Type[] = carteBancairesToCheck.filter(isPresent);
    if (carteBancaires.length > 0) {
      const carteBancaireCollectionIdentifiers = carteBancaireCollection.map(
        carteBancaireItem => this.getCarteBancaireIdentifier(carteBancaireItem)!
      );
      const carteBancairesToAdd = carteBancaires.filter(carteBancaireItem => {
        const carteBancaireIdentifier = this.getCarteBancaireIdentifier(carteBancaireItem);
        if (carteBancaireCollectionIdentifiers.includes(carteBancaireIdentifier)) {
          return false;
        }
        carteBancaireCollectionIdentifiers.push(carteBancaireIdentifier);
        return true;
      });
      return [...carteBancairesToAdd, ...carteBancaireCollection];
    }
    return carteBancaireCollection;
  }

  protected convertDateFromClient<T extends ICarteBancaire | NewCarteBancaire | PartialUpdateCarteBancaire>(carteBancaire: T): RestOf<T> {
    return {
      ...carteBancaire,
      dateExpiration: carteBancaire.dateExpiration?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCarteBancaire: RestCarteBancaire): ICarteBancaire {
    return {
      ...restCarteBancaire,
      dateExpiration: restCarteBancaire.dateExpiration ? dayjs(restCarteBancaire.dateExpiration) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCarteBancaire>): HttpResponse<ICarteBancaire> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCarteBancaire[]>): HttpResponse<ICarteBancaire[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
