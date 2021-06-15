import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BicicletaComentario from './bicicleta-comentario';
import BicicletaComentarioDetail from './bicicleta-comentario-detail';
import BicicletaComentarioUpdate from './bicicleta-comentario-update';
import BicicletaComentarioDeleteDialog from './bicicleta-comentario-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BicicletaComentarioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BicicletaComentarioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BicicletaComentarioDetail} />
      <ErrorBoundaryRoute path={match.url} component={BicicletaComentario} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BicicletaComentarioDeleteDialog} />
  </>
);

export default Routes;
