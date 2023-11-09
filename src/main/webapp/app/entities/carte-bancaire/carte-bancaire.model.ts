import dayjs from 'dayjs/esm';
import { IClient } from 'app/entities/client/client.model';

export interface ICarteBancaire {
  id: number;
  nomPorteur?: string | null;
  numCarte?: number | null;
  dateExpiration?: dayjs.Dayjs | null;
  cvv?: number | null;
  clients?: Pick<IClient, 'id'>[] | null;
}

export type NewCarteBancaire = Omit<ICarteBancaire, 'id'> & { id: null };
