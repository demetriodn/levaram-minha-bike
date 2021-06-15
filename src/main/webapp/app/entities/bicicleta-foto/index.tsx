import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BicicletaFoto from './bicicleta-foto';
import BicicletaFotoDetail from './bicicleta-foto-detail';
import BicicletaFotoUpdate from './bicicleta-foto-update';
import BicicletaFotoDeleteDialog from './bicicleta-foto-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BicicletaFotoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BicicletaFotoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BicicletaFotoDetail} />
      <ErrorBoundaryRoute path={match.url} component={BicicletaFoto} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BicicletaFotoDeleteDialog} />
  </>
);

export default Routes;
