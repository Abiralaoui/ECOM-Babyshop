import { IAdmin, NewAdmin } from './admin.model';

export const sampleWithRequiredData: IAdmin = {
  id: 39001,
};

export const sampleWithPartialData: IAdmin = {
  id: 16851,
  identifiant: 'User-friendly',
  motDePasse: 'Sleek empower Granite',
};

export const sampleWithFullData: IAdmin = {
  id: 71549,
  identifiant: 'exuding incentivize',
  motDePasse: 'Avon Iraqi Stagiaire',
};

export const sampleWithNewData: NewAdmin = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
