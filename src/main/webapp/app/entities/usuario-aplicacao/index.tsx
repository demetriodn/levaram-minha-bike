import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UsuarioAplicacao from './usuario-aplicacao';
import UsuarioAplicacaoDetail from './usuario-aplicacao-detail';
import UsuarioAplicacaoUpdate from './usuario-aplicacao-update';
import UsuarioAplicacaoDeleteDialog from './usuario-aplicacao-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UsuarioAplicacaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UsuarioAplicacaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UsuarioAplicacaoDetail} />
      <ErrorBoundaryRoute path={match.url} component={UsuarioAplicacao} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UsuarioAplicacaoDeleteDialog} />
  </>
);

export default Routes;
