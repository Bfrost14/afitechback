export interface ISemestre {
  id: number;
  nom?: string | null;
}

export type NewSemestre = Omit<ISemestre, 'id'> & { id: null };
