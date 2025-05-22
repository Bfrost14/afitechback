import dayjs from 'dayjs/esm';
import { ISalle } from '../salle/salle.model';
import { IMatiereUtilisateur } from '../matiere-utilisateur/matiere-utilisateur.model';

export interface ICalendrierCours {
  id: number;
  lien: string;
  dateDebut?: dayjs.Dayjs | null;
  dateFin?: dayjs.Dayjs | null;
  matiereUser?: Pick<IMatiereUtilisateur, 'id'> | null;
  salle?: Pick<ISalle, 'id'> | null;
}

export type NewCalendrierCours = Omit<ICalendrierCours, 'id'> & { id: null };
