import dayjs from 'dayjs/esm';

import { ICarteBancaire, NewCarteBancaire } from './carte-bancaire.model';

export const sampleWithRequiredData: ICarteBancaire = {
  id: 72915,
};

export const sampleWithPartialData: ICarteBancaire = {
  id: 2529,
  nomPorteur: 'navigating Books EXE',
  numCarte: 36972,
  dateExpiration: dayjs('2023-11-08T19:49'),
};

export const sampleWithFullData: ICarteBancaire = {
  id: 53790,
  nomPorteur: 'a withdrawal Bedfordshire',
  numCarte: 60233,
  dateExpiration: dayjs('2023-11-09T07:45'),
  cvv: 99434,
};

export const sampleWithNewData: NewCarteBancaire = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
