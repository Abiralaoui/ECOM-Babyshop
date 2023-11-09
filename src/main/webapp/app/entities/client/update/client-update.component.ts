import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ClientFormService, ClientFormGroup } from './client-form.service';
import { IClient } from '../client.model';
import { ClientService } from '../service/client.service';
import { ICarteBancaire } from 'app/entities/carte-bancaire/carte-bancaire.model';
import { CarteBancaireService } from 'app/entities/carte-bancaire/service/carte-bancaire.service';

@Component({
  selector: 'jhi-client-update',
  templateUrl: './client-update.component.html',
})
export class ClientUpdateComponent implements OnInit {
  isSaving = false;
  client: IClient | null = null;

  carteBancairesSharedCollection: ICarteBancaire[] = [];

  editForm: ClientFormGroup = this.clientFormService.createClientFormGroup();

  constructor(
    protected clientService: ClientService,
    protected clientFormService: ClientFormService,
    protected carteBancaireService: CarteBancaireService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCarteBancaire = (o1: ICarteBancaire | null, o2: ICarteBancaire | null): boolean =>
    this.carteBancaireService.compareCarteBancaire(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ client }) => {
      this.client = client;
      if (client) {
        this.updateForm(client);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const client = this.clientFormService.getClient(this.editForm);
    if (client.id !== null) {
      this.subscribeToSaveResponse(this.clientService.update(client));
    } else {
      this.subscribeToSaveResponse(this.clientService.create(client));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClient>>): void {
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

  protected updateForm(client: IClient): void {
    this.client = client;
    this.clientFormService.resetForm(this.editForm, client);

    this.carteBancairesSharedCollection = this.carteBancaireService.addCarteBancaireToCollectionIfMissing<ICarteBancaire>(
      this.carteBancairesSharedCollection,
      ...(client.carteBancaires ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.carteBancaireService
      .query()
      .pipe(map((res: HttpResponse<ICarteBancaire[]>) => res.body ?? []))
      .pipe(
        map((carteBancaires: ICarteBancaire[]) =>
          this.carteBancaireService.addCarteBancaireToCollectionIfMissing<ICarteBancaire>(
            carteBancaires,
            ...(this.client?.carteBancaires ?? [])
          )
        )
      )
      .subscribe((carteBancaires: ICarteBancaire[]) => (this.carteBancairesSharedCollection = carteBancaires));
  }
}
