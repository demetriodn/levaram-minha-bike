import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UsuarioAplicacao from './usuario-aplicacao';
import Evento from './evento';
import Bicicleta from './bicicleta';
import BicicletaFoto from './bicicleta-foto';
import BicicletaComentario from './bicicleta-comentario';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}usuario-aplicacao`} component={UsuarioAplicacao} />
      <ErrorBoundaryRoute path={`${match.url}evento`} component={Evento} />
      <ErrorBoundaryRoute path={`${match.url}bicicleta`} component={Bicicleta} />
      <ErrorBoundaryRoute path={`${match.url}bicicleta-foto`} component={BicicletaFoto} />
      <ErrorBoundaryRoute path={`${match.url}bicicleta-comentario`} component={BicicletaComentario} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
