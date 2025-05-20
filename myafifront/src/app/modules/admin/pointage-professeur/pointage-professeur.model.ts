import dayjs from 'dayjs/esm';
import { IUtilisateur } from '../ue/ue.model';


export interface IPointageProfesseur {
  id: number;
  heureArrivee?: dayjs.Dayjs | null;
  heureDepart?: dayjs.Dayjs | null;
  professeur?: Pick<IUtilisateur, 'id'> | null;
}

export type NewPointageProfesseur = Omit<IPointageProfesseur, 'id'> & { id: null };
