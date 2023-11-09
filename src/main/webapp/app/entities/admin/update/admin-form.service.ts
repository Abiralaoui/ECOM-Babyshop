import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAdmin, NewAdmin } from '../admin.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAdmin for edit and NewAdminFormGroupInput for create.
 */
type AdminFormGroupInput = IAdmin | PartialWithRequiredKeyOf<NewAdmin>;

type AdminFormDefaults = Pick<NewAdmin, 'id'>;

type AdminFormGroupContent = {
  id: FormControl<IAdmin['id'] | NewAdmin['id']>;
  identifiant: FormControl<IAdmin['identifiant']>;
  motDePasse: FormControl<IAdmin['motDePasse']>;
};

export type AdminFormGroup = FormGroup<AdminFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AdminFormService {
  createAdminFormGroup(admin: AdminFormGroupInput = { id: null }): AdminFormGroup {
    const adminRawValue = {
      ...this.getFormDefaults(),
      ...admin,
    };
    return new FormGroup<AdminFormGroupContent>({
      id: new FormControl(
        { value: adminRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      identifiant: new FormControl(adminRawValue.identifiant),
      motDePasse: new FormControl(adminRawValue.motDePasse),
    });
  }

  getAdmin(form: AdminFormGroup): IAdmin | NewAdmin {
    return form.getRawValue() as IAdmin | NewAdmin;
  }

  resetForm(form: AdminFormGroup, admin: AdminFormGroupInput): void {
    const adminRawValue = { ...this.getFormDefaults(), ...admin };
    form.reset(
      {
        ...adminRawValue,
        id: { value: adminRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AdminFormDefaults {
    return {
      id: null,
    };
  }
}
