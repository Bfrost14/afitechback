import dayjs from "dayjs";
import { ICampus } from "../campus/campus.model";
import { IFiliere } from "../filiere/filiere.model";
import { Role } from "../enumerations/role.model";
import { IProfile } from "../profile/profile.model";

export interface IUE {
  id: number;
  nom?: string | null;
}

export type NewUE = Omit<IUE, 'id'> & { id: null };


export interface IUtilisateur {
  id: number;
  lastName?: string | null;
  firstName?: string | null;
  password?: string | null;
  matricule?: string | null;
  nationalite?: string | null;
  email?: string | null;
  profil?: Pick<IProfile, 'id'> | null;
  dateDeNaissance?: dayjs.Dayjs | null;
  telephone?: string | null;
  firstConnection?: boolean | null;
  filiere?: Pick<IFiliere, 'id'> | null;
  campus?: Pick<ICampus, 'id'> | null;
  campuses?: ICampus[] | [];
}

export type NewUtilisateur = Omit<IUtilisateur, 'id'> & { id: null };