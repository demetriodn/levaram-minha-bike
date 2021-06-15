import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBicicletaComentario, defaultValue } from 'app/shared/model/bicicleta-comentario.model';

export const ACTION_TYPES = {
  FETCH_BICICLETACOMENTARIO_LIST: 'bicicletaComentario/FETCH_BICICLETACOMENTARIO_LIST',
  FETCH_BICICLETACOMENTARIO: 'bicicletaComentario/FETCH_BICICLETACOMENTARIO',
  CREATE_BICICLETACOMENTARIO: 'bicicletaComentario/CREATE_BICICLETACOMENTARIO',
  UPDATE_BICICLETACOMENTARIO: 'bicicletaComentario/UPDATE_BICICLETACOMENTARIO',
  PARTIAL_UPDATE_BICICLETACOMENTARIO: 'bicicletaComentario/PARTIAL_UPDATE_BICICLETACOMENTARIO',
  DELETE_BICICLETACOMENTARIO: 'bicicletaComentario/DELETE_BICICLETACOMENTARIO',
  RESET: 'bicicletaComentario/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBicicletaComentario>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type BicicletaComentarioState = Readonly<typeof initialState>;

// Reducer

export default (state: BicicletaComentarioState = initialState, action): BicicletaComentarioState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BICICLETACOMENTARIO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BICICLETACOMENTARIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_BICICLETACOMENTARIO):
    case REQUEST(ACTION_TYPES.UPDATE_BICICLETACOMENTARIO):
    case REQUEST(ACTION_TYPES.DELETE_BICICLETACOMENTARIO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_BICICLETACOMENTARIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_BICICLETACOMENTARIO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BICICLETACOMENTARIO):
    case FAILURE(ACTION_TYPES.CREATE_BICICLETACOMENTARIO):
    case FAILURE(ACTION_TYPES.UPDATE_BICICLETACOMENTARIO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_BICICLETACOMENTARIO):
    case FAILURE(ACTION_TYPES.DELETE_BICICLETACOMENTARIO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BICICLETACOMENTARIO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BICICLETACOMENTARIO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_BICICLETACOMENTARIO):
    case SUCCESS(ACTION_TYPES.UPDATE_BICICLETACOMENTARIO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_BICICLETACOMENTARIO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_BICICLETACOMENTARIO):
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

const apiUrl = 'api/bicicleta-comentarios';

// Actions

export const getEntities: ICrudGetAllAction<IBicicletaComentario> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BICICLETACOMENTARIO_LIST,
  payload: axios.get<IBicicletaComentario>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IBicicletaComentario> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BICICLETACOMENTARIO,
    payload: axios.get<IBicicletaComentario>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IBicicletaComentario> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BICICLETACOMENTARIO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBicicletaComentario> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BICICLETACOMENTARIO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IBicicletaComentario> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_BICICLETACOMENTARIO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBicicletaComentario> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BICICLETACOMENTARIO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
