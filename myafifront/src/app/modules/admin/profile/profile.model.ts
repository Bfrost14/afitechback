import { Authority } from "app/core/config/authority.constants";
import dayjs from "dayjs";

export interface IProfile {
  id: number;
  nom?: string | null;
  authorities?: any[] | []
}

export type NewProfile = Omit<IProfile, 'id'> & { id: null };

