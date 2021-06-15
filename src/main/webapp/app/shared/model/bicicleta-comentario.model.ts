import { IBicicleta } from 'app/shared/model/bicicleta.model';

export interface IBicicletaComentario {
  id?: number;
  observacao?: string | null;
  bicicleta?: IBicicleta | null;
}

export const defaultValue: Readonly<IBicicletaComentario> = {};
