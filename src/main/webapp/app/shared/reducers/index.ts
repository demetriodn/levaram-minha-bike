import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import usuarioAplicacao, {
  UsuarioAplicacaoState
} from 'app/entities/usuario-aplicacao/usuario-aplicacao.reducer';
// prettier-ignore
import evento, {
  EventoState
} from 'app/entities/evento/evento.reducer';
// prettier-ignore
import bicicleta, {
  BicicletaState
} from 'app/entities/bicicleta/bicicleta.reducer';
// prettier-ignore
import bicicletaFoto, {
  BicicletaFotoState
} from 'app/entities/bicicleta-foto/bicicleta-foto.reducer';
// prettier-ignore
import bicicletaComentario, {
  BicicletaComentarioState
} from 'app/entities/bicicleta-comentario/bicicleta-comentario.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly usuarioAplicacao: UsuarioAplicacaoState;
  readonly evento: EventoState;
  readonly bicicleta: BicicletaState;
  readonly bicicletaFoto: BicicletaFotoState;
  readonly bicicletaComentario: BicicletaComentarioState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  usuarioAplicacao,
  evento,
  bicicleta,
  bicicletaFoto,
  bicicletaComentario,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
