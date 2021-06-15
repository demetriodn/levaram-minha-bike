import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Bicicleta from './bicicleta';
import BicicletaDetail from './bicicleta-detail';
import BicicletaUpdate from './bicicleta-update';
import BicicletaDeleteDialog from './bicicleta-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BicicletaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BicicletaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BicicletaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Bicicleta} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BicicletaDeleteDialog} />
  </>
);

export default Routes;
