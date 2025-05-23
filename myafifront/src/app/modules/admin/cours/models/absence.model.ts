import dayjs from 'dayjs/esm';
import { IUtilisateur } from '../../ue/ue.model';
import { ICalendrierCours } from '../../calendrier-cours/calendrier-cours.model';


export interface IAbsence {
  id: number;
  date?: dayjs.Dayjs | null;
  justifie?: boolean | null;
  presence?: boolean | null;
  calendierCours?: Pick<ICalendrierCours, 'id'> | null;
  utilisateur?: Pick<IUtilisateur, 'id'> | null;
}

export type NewAbsence = Omit<IAbsence, 'id'> & { id: null };
