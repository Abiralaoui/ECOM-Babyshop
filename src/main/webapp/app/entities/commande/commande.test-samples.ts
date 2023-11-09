import dayjs from 'dayjs/esm';

import { EtatCommande } from 'app/entities/enumerations/etat-commande.model';
import { TypePayement } from 'app/entities/enumerations/type-payement.model';

import { ICommande, NewCommande } from './commande.model';

export const sampleWithRequiredData: ICommande = {
  id: 5189,
};

export const sampleWithPartialData: ICommande = {
  id: 42552,
  date: dayjs('2023-11-08T10:13'),
  typePayement: TypePayement['CB'],
};

export const sampleWithFullData: ICommande = {
  id: 20165,
  date: dayjs('2023-11-08T11:33'),
  etat: EtatCommande['ANNULEE'],
  typePayement: TypePayement['CB'],
};

export const sampleWithNewData: NewCommande = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
