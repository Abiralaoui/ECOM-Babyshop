import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILigneCommande } from '../ligne-commande.model';
import { LigneCommandeService } from '../service/ligne-commande.service';

@Injectable({ providedIn: 'root' })
export class LigneCommandeRoutingResolveService implements Resolve<ILigneCommande | null> {
  constructor(protected service: LigneCommandeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILigneCommande | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ligneCommande: HttpResponse<ILigneCommande>) => {
          if (ligneCommande.body) {
            return of(ligneCommande.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
