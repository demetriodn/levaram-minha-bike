import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBicicletaFoto, defaultValue } from 'app/shared/model/bicicleta-foto.model';

export const ACTION_TYPES = {
  FETCH_BICICLETAFOTO_LIST: 'bicicletaFoto/FETCH_BICICLETAFOTO_LIST',
  FETCH_BICICLETAFOTO: 'bicicletaFoto/FETCH_BICICLETAFOTO',
  CREATE_BICICLETAFOTO: 'bicicletaFoto/CREATE_BICICLETAFOTO',
  UPDATE_BICICLETAFOTO: 'bicicletaFoto/UPDATE_BICICLETAFOTO',
  PARTIAL_UPDATE_BICICLETAFOTO: 'bicicletaFoto/PARTIAL_UPDATE_BICICLETAFOTO',
  DELETE_BICICLETAFOTO: 'bicicletaFoto/DELETE_BICICLETAFOTO',
  RESET: 'bicicletaFoto/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBicicletaFoto>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type BicicletaFotoState = Readonly<typeof initialState>;

// Reducer

export default (state: BicicletaFotoState = initialState, action): BicicletaFotoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BICICLETAFOTO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BICICLETAFOTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_BICICLETAFOTO):
    case REQUEST(ACTION_TYPES.UPDATE_BICICLETAFOTO):
    case REQUEST(ACTION_TYPES.DELETE_BICICLETAFOTO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_BICICLETAFOTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_BICICLETAFOTO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BICICLETAFOTO):
    case FAILURE(ACTION_TYPES.CREATE_BICICLETAFOTO):
    case FAILURE(ACTION_TYPES.UPDATE_BICICLETAFOTO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_BICICLETAFOTO):
    case FAILURE(ACTION_TYPES.DELETE_BICICLETAFOTO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BICICLETAFOTO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BICICLETAFOTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_BICICLETAFOTO):
    case SUCCESS(ACTION_TYPES.UPDATE_BICICLETAFOTO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_BICICLETAFOTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_BICICLETAFOTO):
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

const apiUrl = 'api/bicicleta-fotos';

// Actions

export const getEntities: ICrudGetAllAction<IBicicletaFoto> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BICICLETAFOTO_LIST,
  payload: axios.get<IBicicletaFoto>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IBicicletaFoto> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BICICLETAFOTO,
    payload: axios.get<IBicicletaFoto>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IBicicletaFoto> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BICICLETAFOTO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBicicletaFoto> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BICICLETAFOTO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IBicicletaFoto> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_BICICLETAFOTO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBicicletaFoto> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BICICLETAFOTO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
