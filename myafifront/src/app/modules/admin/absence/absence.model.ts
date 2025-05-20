import dayjs from 'dayjs/esm';
import { ICours } from '../cours/cours.model';
import { IUtilisateur } from '../ue/ue.model';


export interface IAbsence {
  id: number;
  date?: dayjs.Dayjs | null;
  justifie?: boolean | null;
  cours?: Pick<ICours, 'id'> | null;
  utilisateur?: Pick<IUtilisateur, 'id'> | null;
}

export type NewAbsence = Omit<IAbsence, 'id'> & { id: null };
