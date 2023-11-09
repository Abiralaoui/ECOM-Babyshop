import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CommandeFormService, CommandeFormGroup } from './commande-form.service';
import { ICommande } from '../commande.model';
import { CommandeService } from '../service/commande.service';
import { ICarteBancaire } from 'app/entities/carte-bancaire/carte-bancaire.model';
import { CarteBancaireService } from 'app/entities/carte-bancaire/service/carte-bancaire.service';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';
import { EtatCommande } from 'app/entities/enumerations/etat-commande.model';
import { TypePayement } from 'app/entities/enumerations/type-payement.model';

@Component({
  selector: 'jhi-commande-update',
  templateUrl: './commande-update.component.html',
})
export class CommandeUpdateComponent implements OnInit {
  isSaving = false;
  commande: ICommande | null = null;
  etatCommandeValues = Object.keys(EtatCommande);
  typePayementValues = Object.keys(TypePayement);

  carteBancairesSharedCollection: ICarteBancaire[] = [];
  clientsSharedCollection: IClient[] = [];

  editForm: CommandeFormGroup = this.commandeFormService.createCommandeFormGroup();

  constructor(
    protected commandeService: CommandeService,
    protected commandeFormService: CommandeFormService,
    protected carteBancaireService: CarteBancaireService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCarteBancaire = (o1: ICarteBancaire | null, o2: ICarteBancaire | null): boolean =>
    this.carteBancaireService.compareCarteBancaire(o1, o2);

  compareClient = (o1: IClient | null, o2: IClient | null): boolean => this.clientService.compareClient(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commande }) => {
      this.commande = commande;
      if (commande) {
        this.updateForm(commande);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commande = this.commandeFormService.getCommande(this.editForm);
    if (commande.id !== null) {
      this.subscribeToSaveResponse(this.commandeService.update(commande));
    } else {
      this.subscribeToSaveResponse(this.commandeService.create(commande));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommande>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(commande: ICommande): void {
    this.commande = commande;
    this.commandeFormService.resetForm(this.editForm, commande);

    this.carteBancairesSharedCollection = this.carteBancaireService.addCarteBancaireToCollectionIfMissing<ICarteBancaire>(
      this.carteBancairesSharedCollection,
      commande.carteBancaire
    );
    this.clientsSharedCollection = this.clientService.addClientToCollectionIfMissing<IClient>(
      this.clientsSharedCollection,
      commande.client
    );
  }

  protected loadRelationshipsOptions(): void {
    this.carteBancaireService
      .query()
      .pipe(map((res: HttpResponse<ICarteBancaire[]>) => res.body ?? []))
      .pipe(
        map((carteBancaires: ICarteBancaire[]) =>
          this.carteBancaireService.addCarteBancaireToCollectionIfMissing<ICarteBancaire>(carteBancaires, this.commande?.carteBancaire)
        )
      )
      .subscribe((carteBancaires: ICarteBancaire[]) => (this.carteBancairesSharedCollection = carteBancaires));

    this.clientService
      .query()
      .pipe(map((res: HttpResponse<IClient[]>) => res.body ?? []))
      .pipe(map((clients: IClient[]) => this.clientService.addClientToCollectionIfMissing<IClient>(clients, this.commande?.client)))
      .subscribe((clients: IClient[]) => (this.clientsSharedCollection = clients));
  }
}
