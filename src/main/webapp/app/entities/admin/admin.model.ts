export interface IAdmin {
  id: number;
  identifiant?: string | null;
  motDePasse?: string | null;
}

export type NewAdmin = Omit<IAdmin, 'id'> & { id: null };
