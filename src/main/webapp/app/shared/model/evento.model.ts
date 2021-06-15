import dayjs from 'dayjs';
import { IBicicleta } from 'app/shared/model/bicicleta.model';
import { TipoEvento } from 'app/shared/model/enumerations/tipo-evento.model';
import { TipoLocal } from 'app/shared/model/enumerations/tipo-local.model';
import { FormaFixacao } from 'app/shared/model/enumerations/forma-fixacao.model';
import { MetodoCoercao } from 'app/shared/model/enumerations/metodo-coercao.model';

export interface IEvento {
  id?: number;
  dataHoraEvento?: string;
  tipoEvento?: TipoEvento;
  tipoLocal?: TipoLocal;
  coordenadaLocal?: string | null;
  detalhesLocal?: string | null;
  dataHoraCriacaoRegistro?: string | null;
  descricaoEvento?: string | null;
  formaFixacao?: FormaFixacao | null;
  metodoCoercao?: MetodoCoercao | null;
  numEnvolvidosAssaltantes?: number | null;
  numEnvolvidosVitimas?: number | null;
  bicicletas?: IBicicleta[] | null;
}

export const defaultValue: Readonly<IEvento> = {};
