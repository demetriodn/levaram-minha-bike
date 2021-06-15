import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUsuarioAplicacao, defaultValue } from 'app/shared/model/usuario-aplicacao.model';

export const ACTION_TYPES = {
  FETCH_USUARIOAPLICACAO_LIST: 'usuarioAplicacao/FETCH_USUARIOAPLICACAO_LIST',
  FETCH_USUARIOAPLICACAO: 'usuarioAplicacao/FETCH_USUARIOAPLICACAO',
  CREATE_USUARIOAPLICACAO: 'usuarioAplicacao/CREATE_USUARIOAPLICACAO',
  UPDATE_USUARIOAPLICACAO: 'usuarioAplicacao/UPDATE_USUARIOAPLICACAO',
  PARTIAL_UPDATE_USUARIOAPLICACAO: 'usuarioAplicacao/PARTIAL_UPDATE_USUARIOAPLICACAO',
  DELETE_USUARIOAPLICACAO: 'usuarioAplicacao/DELETE_USUARIOAPLICACAO',
  RESET: 'usuarioAplicacao/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUsuarioAplicacao>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type UsuarioAplicacaoState = Readonly<typeof initialState>;

// Reducer

export default (state: UsuarioAplicacaoState = initialState, action): UsuarioAplicacaoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USUARIOAPLICACAO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USUARIOAPLICACAO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_USUARIOAPLICACAO):
    case REQUEST(ACTION_TYPES.UPDATE_USUARIOAPLICACAO):
    case REQUEST(ACTION_TYPES.DELETE_USUARIOAPLICACAO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_USUARIOAPLICACAO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_USUARIOAPLICACAO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USUARIOAPLICACAO):
    case FAILURE(ACTION_TYPES.CREATE_USUARIOAPLICACAO):
    case FAILURE(ACTION_TYPES.UPDATE_USUARIOAPLICACAO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_USUARIOAPLICACAO):
    case FAILURE(ACTION_TYPES.DELETE_USUARIOAPLICACAO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_USUARIOAPLICACAO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_USUARIOAPLICACAO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_USUARIOAPLICACAO):
    case SUCCESS(ACTION_TYPES.UPDATE_USUARIOAPLICACAO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_USUARIOAPLICACAO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_USUARIOAPLICACAO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/usuario-aplicacaos';

// Actions

export const getEntities: ICrudGetAllAction<IUsuarioAplicacao> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_USUARIOAPLICACAO_LIST,
  payload: axios.get<IUsuarioAplicacao>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IUsuarioAplicacao> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USUARIOAPLICACAO,
    payload: axios.get<IUsuarioAplicacao>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IUsuarioAplicacao> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USUARIOAPLICACAO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUsuarioAplicacao> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USUARIOAPLICACAO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IUsuarioAplicacao> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_USUARIOAPLICACAO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUsuarioAplicacao> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USUARIOAPLICACAO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
