import { IProduit, NewProduit } from './produit.model';

export const sampleWithRequiredData: IProduit = {
  id: 91013,
};

export const sampleWithPartialData: IProduit = {
  id: 63349,
  prixUnitaire: 26916,
  couleur: 'multi-tasking',
};

export const sampleWithFullData: IProduit = {
  id: 51446,
  idProduit: 2823,
  libelle: 'Superviseur deposit CSS',
  description: 'Sleek input deposit',
  prixUnitaire: 89776,
  taille: 45890,
  couleur: 'Unbranded Auvergne sensor',
};

export const sampleWithNewData: NewProduit = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
