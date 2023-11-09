import { IClient, NewClient } from './client.model';

export const sampleWithRequiredData: IClient = {
  id: 71655,
};

export const sampleWithPartialData: IClient = {
  id: 91535,
  motDePasse: 'Salad Concrete a',
};

export const sampleWithFullData: IClient = {
  id: 51037,
  identifiant: 'programming homogeneous',
  motDePasse: 'Bedfordshire',
  tel: '5987730277',
  address: 'Steel',
};

export const sampleWithNewData: NewClient = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
