import dayjs from 'dayjs/esm';
import { IProduit } from 'app/entities/produit/produit.model';
import { IClient } from 'app/entities/client/client.model';

export interface IAvis {
  id: number;
  note?: number | null;
  commentaire?: string | null;
  date?: dayjs.Dayjs | null;
  produit?: Pick<IProduit, 'id'> | null;
  client?: Pick<IClient, 'id'> | null;
}

export type NewAvis = Omit<IAvis, 'id'> & { id: null };
