import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAvis, NewAvis } from '../avis.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAvis for edit and NewAvisFormGroupInput for create.
 */
type AvisFormGroupInput = IAvis | PartialWithRequiredKeyOf<NewAvis>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAvis | NewAvis> = Omit<T, 'date'> & {
  date?: string | null;
};

type AvisFormRawValue = FormValueOf<IAvis>;

type NewAvisFormRawValue = FormValueOf<NewAvis>;

type AvisFormDefaults = Pick<NewAvis, 'id' | 'date'>;

type AvisFormGroupContent = {
  id: FormControl<AvisFormRawValue['id'] | NewAvis['id']>;
  note: FormControl<AvisFormRawValue['note']>;
  commentaire: FormControl<AvisFormRawValue['commentaire']>;
  date: FormControl<AvisFormRawValue['date']>;
  produit: FormControl<AvisFormRawValue['produit']>;
  client: FormControl<AvisFormRawValue['client']>;
};

export type AvisFormGroup = FormGroup<AvisFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AvisFormService {
  createAvisFormGroup(avis: AvisFormGroupInput = { id: null }): AvisFormGroup {
    const avisRawValue = this.convertAvisToAvisRawValue({
      ...this.getFormDefaults(),
      ...avis,
    });
    return new FormGroup<AvisFormGroupContent>({
      id: new FormControl(
        { value: avisRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      note: new FormControl(avisRawValue.note, {
        validators: [Validators.min(1), Validators.max(5)],
      }),
      commentaire: new FormControl(avisRawValue.commentaire),
      date: new FormControl(avisRawValue.date),
      produit: new FormControl(avisRawValue.produit),
      client: new FormControl(avisRawValue.client),
    });
  }

  getAvis(form: AvisFormGroup): IAvis | NewAvis {
    return this.convertAvisRawValueToAvis(form.getRawValue() as AvisFormRawValue | NewAvisFormRawValue);
  }

  resetForm(form: AvisFormGroup, avis: AvisFormGroupInput): void {
    const avisRawValue = this.convertAvisToAvisRawValue({ ...this.getFormDefaults(), ...avis });
    form.reset(
      {
        ...avisRawValue,
        id: { value: avisRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AvisFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      date: currentTime,
    };
  }

  private convertAvisRawValueToAvis(rawAvis: AvisFormRawValue | NewAvisFormRawValue): IAvis | NewAvis {
    return {
      ...rawAvis,
      date: dayjs(rawAvis.date, DATE_TIME_FORMAT),
    };
  }

  private convertAvisToAvisRawValue(
    avis: IAvis | (Partial<NewAvis> & AvisFormDefaults)
  ): AvisFormRawValue | PartialWithRequiredKeyOf<NewAvisFormRawValue> {
    return {
      ...avis,
      date: avis.date ? avis.date.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
