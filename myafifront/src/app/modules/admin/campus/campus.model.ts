export interface ICampus {
  id: number;
  nom?: string | null;
}

export type NewCampus = Omit<ICampus, 'id'> & { id: null };
