import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction,
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBicicleta, defaultValue } from 'app/shared/model/bicicleta.model';

export const ACTION_TYPES = {
  FETCH_BICICLETA_LIST: 'bicicleta/FETCH_BICICLETA_LIST',
  FETCH_BICICLETA: 'bicicleta/FETCH_BICICLETA',
  CREATE_BICICLETA: 'bicicleta/CREATE_BICICLETA',
  UPDATE_BICICLETA: 'bicicleta/UPDATE_BICICLETA',
  PARTIAL_UPDATE_BICICLETA: 'bicicleta/PARTIAL_UPDATE_BICICLETA',
  DELETE_BICICLETA: 'bicicleta/DELETE_BICICLETA',
  SET_BLOB: 'bicicleta/SET_BLOB',
  RESET: 'bicicleta/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBicicleta>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type BicicletaState = Readonly<typeof initialState>;

// Reducer

export default (state: BicicletaState = initialState, action): BicicletaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BICICLETA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BICICLETA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_BICICLETA):
    case REQUEST(ACTION_TYPES.UPDATE_BICICLETA):
    case REQUEST(ACTION_TYPES.DELETE_BICICLETA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_BICICLETA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_BICICLETA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BICICLETA):
    case FAILURE(ACTION_TYPES.CREATE_BICICLETA):
    case FAILURE(ACTION_TYPES.UPDATE_BICICLETA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_BICICLETA):
    case FAILURE(ACTION_TYPES.DELETE_BICICLETA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BICICLETA_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_BICICLETA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_BICICLETA):
    case SUCCESS(ACTION_TYPES.UPDATE_BICICLETA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_BICICLETA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_BICICLETA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/bicicletas';

// Actions

export const getEntities: ICrudGetAllAction<IBicicleta> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_BICICLETA_LIST,
    payload: axios.get<IBicicleta>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IBicicleta> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BICICLETA,
    payload: axios.get<IBicicleta>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IBicicleta> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BICICLETA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IBicicleta> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BICICLETA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IBicicleta> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_BICICLETA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBicicleta> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BICICLETA,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
