import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICarteBancaire } from '../carte-bancaire.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../carte-bancaire.test-samples';

import { CarteBancaireService, RestCarteBancaire } from './carte-bancaire.service';

const requireRestSample: RestCarteBancaire = {
  ...sampleWithRequiredData,
  dateExpiration: sampleWithRequiredData.dateExpiration?.toJSON(),
};

describe('CarteBancaire Service', () => {
  let service: CarteBancaireService;
  let httpMock: HttpTestingController;
  let expectedResult: ICarteBancaire | ICarteBancaire[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CarteBancaireService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a CarteBancaire', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const carteBancaire = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(carteBancaire).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CarteBancaire', () => {
      const carteBancaire = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(carteBancaire).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CarteBancaire', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CarteBancaire', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CarteBancaire', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCarteBancaireToCollectionIfMissing', () => {
      it('should add a CarteBancaire to an empty array', () => {
        const carteBancaire: ICarteBancaire = sampleWithRequiredData;
        expectedResult = service.addCarteBancaireToCollectionIfMissing([], carteBancaire);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(carteBancaire);
      });

      it('should not add a CarteBancaire to an array that contains it', () => {
        const carteBancaire: ICarteBancaire = sampleWithRequiredData;
        const carteBancaireCollection: ICarteBancaire[] = [
          {
            ...carteBancaire,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCarteBancaireToCollectionIfMissing(carteBancaireCollection, carteBancaire);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CarteBancaire to an array that doesn't contain it", () => {
        const carteBancaire: ICarteBancaire = sampleWithRequiredData;
        const carteBancaireCollection: ICarteBancaire[] = [sampleWithPartialData];
        expectedResult = service.addCarteBancaireToCollectionIfMissing(carteBancaireCollection, carteBancaire);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(carteBancaire);
      });

      it('should add only unique CarteBancaire to an array', () => {
        const carteBancaireArray: ICarteBancaire[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const carteBancaireCollection: ICarteBancaire[] = [sampleWithRequiredData];
        expectedResult = service.addCarteBancaireToCollectionIfMissing(carteBancaireCollection, ...carteBancaireArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const carteBancaire: ICarteBancaire = sampleWithRequiredData;
        const carteBancaire2: ICarteBancaire = sampleWithPartialData;
        expectedResult = service.addCarteBancaireToCollectionIfMissing([], carteBancaire, carteBancaire2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(carteBancaire);
        expect(expectedResult).toContain(carteBancaire2);
      });

      it('should accept null and undefined values', () => {
        const carteBancaire: ICarteBancaire = sampleWithRequiredData;
        expectedResult = service.addCarteBancaireToCollectionIfMissing([], null, carteBancaire, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(carteBancaire);
      });

      it('should return initial array if no CarteBancaire is added', () => {
        const carteBancaireCollection: ICarteBancaire[] = [sampleWithRequiredData];
        expectedResult = service.addCarteBancaireToCollectionIfMissing(carteBancaireCollection, undefined, null);
        expect(expectedResult).toEqual(carteBancaireCollection);
      });
    });

    describe('compareCarteBancaire', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCarteBancaire(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCarteBancaire(entity1, entity2);
        const compareResult2 = service.compareCarteBancaire(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCarteBancaire(entity1, entity2);
        const compareResult2 = service.compareCarteBancaire(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCarteBancaire(entity1, entity2);
        const compareResult2 = service.compareCarteBancaire(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
