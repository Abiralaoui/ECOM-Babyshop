import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILigneCommande, NewLigneCommande } from '../ligne-commande.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILigneCommande for edit and NewLigneCommandeFormGroupInput for create.
 */
type LigneCommandeFormGroupInput = ILigneCommande | PartialWithRequiredKeyOf<NewLigneCommande>;

type LigneCommandeFormDefaults = Pick<NewLigneCommande, 'id'>;

type LigneCommandeFormGroupContent = {
  id: FormControl<ILigneCommande['id'] | NewLigneCommande['id']>;
  quantite: FormControl<ILigneCommande['quantite']>;
  prix: FormControl<ILigneCommande['prix']>;
  commande: FormControl<ILigneCommande['commande']>;
  produit: FormControl<ILigneCommande['produit']>;
};

export type LigneCommandeFormGroup = FormGroup<LigneCommandeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LigneCommandeFormService {
  createLigneCommandeFormGroup(ligneCommande: LigneCommandeFormGroupInput = { id: null }): LigneCommandeFormGroup {
    const ligneCommandeRawValue = {
      ...this.getFormDefaults(),
      ...ligneCommande,
    };
    return new FormGroup<LigneCommandeFormGroupContent>({
      id: new FormControl(
        { value: ligneCommandeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      quantite: new FormControl(ligneCommandeRawValue.quantite, {
        validators: [Validators.min(0)],
      }),
      prix: new FormControl(ligneCommandeRawValue.prix, {
        validators: [Validators.min(0)],
      }),
      commande: new FormControl(ligneCommandeRawValue.commande),
      produit: new FormControl(ligneCommandeRawValue.produit),
    });
  }

  getLigneCommande(form: LigneCommandeFormGroup): ILigneCommande | NewLigneCommande {
    return form.getRawValue() as ILigneCommande | NewLigneCommande;
  }

  resetForm(form: LigneCommandeFormGroup, ligneCommande: LigneCommandeFormGroupInput): void {
    const ligneCommandeRawValue = { ...this.getFormDefaults(), ...ligneCommande };
    form.reset(
      {
        ...ligneCommandeRawValue,
        id: { value: ligneCommandeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LigneCommandeFormDefaults {
    return {
      id: null,
    };
  }
}
