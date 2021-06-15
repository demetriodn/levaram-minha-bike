import { IUser } from 'app/shared/model/user.model';

export interface IUsuarioAplicacao {
  id?: number;
  contato?: string | null;
  internalUser?: IUser | null;
}

export const defaultValue: Readonly<IUsuarioAplicacao> = {};
