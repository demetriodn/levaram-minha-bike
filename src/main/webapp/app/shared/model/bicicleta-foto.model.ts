import { IBicicleta } from 'app/shared/model/bicicleta.model';

export interface IBicicletaFoto {
  id?: number;
  urlImagem?: string | null;
  bicicleta?: IBicicleta | null;
}

export const defaultValue: Readonly<IBicicletaFoto> = {};
