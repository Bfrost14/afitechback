import dayjs from 'dayjs/esm';
import { ICours } from '../cours/cours.model';
import { ISalle } from '../salle/salle.model';
import { IFiliere } from '../filiere/filiere.model';

export interface ICalendrierCours {
  id: number;
  dateDebut?: dayjs.Dayjs | null;
  dateFin?: dayjs.Dayjs | null;
  cours?: Pick<ICours, 'id'> | null;
  salle?: Pick<ISalle, 'id'> | null;
  filiere?: Pick<IFiliere, 'id'> | null;
}

export type NewCalendrierCours = Omit<ICalendrierCours, 'id'> & { id: null };
