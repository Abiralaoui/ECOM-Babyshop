import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILigneCommande, NewLigneCommande } from '../ligne-commande.model';

export type PartialUpdateLigneCommande = Partial<ILigneCommande> & Pick<ILigneCommande, 'id'>;

export type EntityResponseType = HttpResponse<ILigneCommande>;
export type EntityArrayResponseType = HttpResponse<ILigneCommande[]>;

@Injectable({ providedIn: 'root' })
export class LigneCommandeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ligne-commandes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ligneCommande: NewLigneCommande): Observable<EntityResponseType> {
    return this.http.post<ILigneCommande>(this.resourceUrl, ligneCommande, { observe: 'response' });
  }

  update(ligneCommande: ILigneCommande): Observable<EntityResponseType> {
    return this.http.put<ILigneCommande>(`${this.resourceUrl}/${this.getLigneCommandeIdentifier(ligneCommande)}`, ligneCommande, {
      observe: 'response',
    });
  }

  partialUpdate(ligneCommande: PartialUpdateLigneCommande): Observable<EntityResponseType> {
    return this.http.patch<ILigneCommande>(`${this.resourceUrl}/${this.getLigneCommandeIdentifier(ligneCommande)}`, ligneCommande, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILigneCommande>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILigneCommande[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLigneCommandeIdentifier(ligneCommande: Pick<ILigneCommande, 'id'>): number {
    return ligneCommande.id;
  }

  compareLigneCommande(o1: Pick<ILigneCommande, 'id'> | null, o2: Pick<ILigneCommande, 'id'> | null): boolean {
    return o1 && o2 ? this.getLigneCommandeIdentifier(o1) === this.getLigneCommandeIdentifier(o2) : o1 === o2;
  }

  addLigneCommandeToCollectionIfMissing<Type extends Pick<ILigneCommande, 'id'>>(
    ligneCommandeCollection: Type[],
    ...ligneCommandesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ligneCommandes: Type[] = ligneCommandesToCheck.filter(isPresent);
    if (ligneCommandes.length > 0) {
      const ligneCommandeCollectionIdentifiers = ligneCommandeCollection.map(
        ligneCommandeItem => this.getLigneCommandeIdentifier(ligneCommandeItem)!
      );
      const ligneCommandesToAdd = ligneCommandes.filter(ligneCommandeItem => {
        const ligneCommandeIdentifier = this.getLigneCommandeIdentifier(ligneCommandeItem);
        if (ligneCommandeCollectionIdentifiers.includes(ligneCommandeIdentifier)) {
          return false;
        }
        ligneCommandeCollectionIdentifiers.push(ligneCommandeIdentifier);
        return true;
      });
      return [...ligneCommandesToAdd, ...ligneCommandeCollection];
    }
    return ligneCommandeCollection;
  }
}
