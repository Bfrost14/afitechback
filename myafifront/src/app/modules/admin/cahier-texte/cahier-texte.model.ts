import dayjs from 'dayjs/esm';
import { ICours } from '../cours/cours.model';
import { IUtilisateur } from '../ue/ue.model';

export interface ICahierTexte {
  id: number;
  date?: dayjs.Dayjs | null;
  contenu?: string | null;
  cours?: Pick<ICours, 'id'> | null;
  utilisateur?: Pick<IUtilisateur, 'id'> | null;
}

export type NewCahierTexte = Omit<ICahierTexte, 'id'> & { id: null };
