import { IBicicletaFoto } from 'app/shared/model/bicicleta-foto.model';
import { IBicicletaComentario } from 'app/shared/model/bicicleta-comentario.model';
import { IEvento } from 'app/shared/model/evento.model';
import { StatusBicicleta } from 'app/shared/model/enumerations/status-bicicleta.model';

export interface IBicicleta {
  id?: number;
  descricao?: string;
  status?: StatusBicicleta;
  numeroQuadro?: string | null;
  numeroBikeRegistrada?: string | null;
  bicicletaFotos?: IBicicletaFoto[] | null;
  bicicletaComentarios?: IBicicletaComentario[] | null;
  evento?: IEvento | null;
}

export const defaultValue: Readonly<IBicicleta> = {};
