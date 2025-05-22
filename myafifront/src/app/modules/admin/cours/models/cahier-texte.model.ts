import dayjs from 'dayjs/esm';
import { IUtilisateur } from '../../ue/ue.model';
import { ICalendrierCours } from '../../calendrier-cours/calendrier-cours.model';

export interface ICahierTexte {
  id: number;
  date?: dayjs.Dayjs | null;
  contenu?: string | null;
  calendrierCours?: Pick<ICalendrierCours, 'id'> | null;
  utilisateur?: Pick<IUtilisateur, 'id'> | null;
}

export type NewCahierTexte = Omit<ICahierTexte, 'id'> & { id: null };
