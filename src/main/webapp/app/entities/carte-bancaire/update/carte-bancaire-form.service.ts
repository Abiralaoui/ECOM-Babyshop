import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICarteBancaire, NewCarteBancaire } from '../carte-bancaire.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICarteBancaire for edit and NewCarteBancaireFormGroupInput for create.
 */
type CarteBancaireFormGroupInput = ICarteBancaire | PartialWithRequiredKeyOf<NewCarteBancaire>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICarteBancaire | NewCarteBancaire> = Omit<T, 'dateExpiration'> & {
  dateExpiration?: string | null;
};

type CarteBancaireFormRawValue = FormValueOf<ICarteBancaire>;

type NewCarteBancaireFormRawValue = FormValueOf<NewCarteBancaire>;

type CarteBancaireFormDefaults = Pick<NewCarteBancaire, 'id' | 'dateExpiration' | 'clients'>;

type CarteBancaireFormGroupContent = {
  id: FormControl<CarteBancaireFormRawValue['id'] | NewCarteBancaire['id']>;
  nomPorteur: FormControl<CarteBancaireFormRawValue['nomPorteur']>;
  numCarte: FormControl<CarteBancaireFormRawValue['numCarte']>;
  dateExpiration: FormControl<CarteBancaireFormRawValue['dateExpiration']>;
  cvv: FormControl<CarteBancaireFormRawValue['cvv']>;
  clients: FormControl<CarteBancaireFormRawValue['clients']>;
};

export type CarteBancaireFormGroup = FormGroup<CarteBancaireFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CarteBancaireFormService {
  createCarteBancaireFormGroup(carteBancaire: CarteBancaireFormGroupInput = { id: null }): CarteBancaireFormGroup {
    const carteBancaireRawValue = this.convertCarteBancaireToCarteBancaireRawValue({
      ...this.getFormDefaults(),
      ...carteBancaire,
    });
    return new FormGroup<CarteBancaireFormGroupContent>({
      id: new FormControl(
        { value: carteBancaireRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nomPorteur: new FormControl(carteBancaireRawValue.nomPorteur),
      numCarte: new FormControl(carteBancaireRawValue.numCarte),
      dateExpiration: new FormControl(carteBancaireRawValue.dateExpiration),
      cvv: new FormControl(carteBancaireRawValue.cvv),
      clients: new FormControl(carteBancaireRawValue.clients ?? []),
    });
  }

  getCarteBancaire(form: CarteBancaireFormGroup): ICarteBancaire | NewCarteBancaire {
    return this.convertCarteBancaireRawValueToCarteBancaire(form.getRawValue() as CarteBancaireFormRawValue | NewCarteBancaireFormRawValue);
  }

  resetForm(form: CarteBancaireFormGroup, carteBancaire: CarteBancaireFormGroupInput): void {
    const carteBancaireRawValue = this.convertCarteBancaireToCarteBancaireRawValue({ ...this.getFormDefaults(), ...carteBancaire });
    form.reset(
      {
        ...carteBancaireRawValue,
        id: { value: carteBancaireRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CarteBancaireFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dateExpiration: currentTime,
      clients: [],
    };
  }

  private convertCarteBancaireRawValueToCarteBancaire(
    rawCarteBancaire: CarteBancaireFormRawValue | NewCarteBancaireFormRawValue
  ): ICarteBancaire | NewCarteBancaire {
    return {
      ...rawCarteBancaire,
      dateExpiration: dayjs(rawCarteBancaire.dateExpiration, DATE_TIME_FORMAT),
    };
  }

  private convertCarteBancaireToCarteBancaireRawValue(
    carteBancaire: ICarteBancaire | (Partial<NewCarteBancaire> & CarteBancaireFormDefaults)
  ): CarteBancaireFormRawValue | PartialWithRequiredKeyOf<NewCarteBancaireFormRawValue> {
    return {
      ...carteBancaire,
      dateExpiration: carteBancaire.dateExpiration ? carteBancaire.dateExpiration.format(DATE_TIME_FORMAT) : undefined,
      clients: carteBancaire.clients ?? [],
    };
  }
}
