import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../carte-bancaire.test-samples';

import { CarteBancaireFormService } from './carte-bancaire-form.service';

describe('CarteBancaire Form Service', () => {
  let service: CarteBancaireFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CarteBancaireFormService);
  });

  describe('Service methods', () => {
    describe('createCarteBancaireFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCarteBancaireFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomPorteur: expect.any(Object),
            numCarte: expect.any(Object),
            dateExpiration: expect.any(Object),
            cvv: expect.any(Object),
            clients: expect.any(Object),
          })
        );
      });

      it('passing ICarteBancaire should create a new form with FormGroup', () => {
        const formGroup = service.createCarteBancaireFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomPorteur: expect.any(Object),
            numCarte: expect.any(Object),
            dateExpiration: expect.any(Object),
            cvv: expect.any(Object),
            clients: expect.any(Object),
          })
        );
      });
    });

    describe('getCarteBancaire', () => {
      it('should return NewCarteBancaire for default CarteBancaire initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCarteBancaireFormGroup(sampleWithNewData);

        const carteBancaire = service.getCarteBancaire(formGroup) as any;

        expect(carteBancaire).toMatchObject(sampleWithNewData);
      });

      it('should return NewCarteBancaire for empty CarteBancaire initial value', () => {
        const formGroup = service.createCarteBancaireFormGroup();

        const carteBancaire = service.getCarteBancaire(formGroup) as any;

        expect(carteBancaire).toMatchObject({});
      });

      it('should return ICarteBancaire', () => {
        const formGroup = service.createCarteBancaireFormGroup(sampleWithRequiredData);

        const carteBancaire = service.getCarteBancaire(formGroup) as any;

        expect(carteBancaire).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICarteBancaire should not enable id FormControl', () => {
        const formGroup = service.createCarteBancaireFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCarteBancaire should disable id FormControl', () => {
        const formGroup = service.createCarteBancaireFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
