import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './bicicleta-comentario.reducer';
import { IBicicletaComentario } from 'app/shared/model/bicicleta-comentario.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBicicletaComentarioProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const BicicletaComentario = (props: IBicicletaComentarioProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { bicicletaComentarioList, match, loading } = props;
  return (
    <div>
      <h2 id="bicicleta-comentario-heading" data-cy="BicicletaComentarioHeading">
        <Translate contentKey="levaramMinhaBikeApp.bicicletaComentario.home.title">Bicicleta Comentarios</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="levaramMinhaBikeApp.bicicletaComentario.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="levaramMinhaBikeApp.bicicletaComentario.home.createLabel">Create new Bicicleta Comentario</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bicicletaComentarioList && bicicletaComentarioList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="levaramMinhaBikeApp.bicicletaComentario.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="levaramMinhaBikeApp.bicicletaComentario.observacao">Observacao</Translate>
                </th>
                <th>
                  <Translate contentKey="levaramMinhaBikeApp.bicicletaComentario.bicicleta">Bicicleta</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bicicletaComentarioList.map((bicicletaComentario, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${bicicletaComentario.id}`} color="link" size="sm">
                      {bicicletaComentario.id}
                    </Button>
                  </td>
                  <td>{bicicletaComentario.observacao}</td>
                  <td>
                    {bicicletaComentario.bicicleta ? (
                      <Link to={`bicicleta/${bicicletaComentario.bicicleta.id}`}>{bicicletaComentario.bicicleta.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${bicicletaComentario.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${bicicletaComentario.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${bicicletaComentario.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="levaramMinhaBikeApp.bicicletaComentario.home.notFound">No Bicicleta Comentarios found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ bicicletaComentario }: IRootState) => ({
  bicicletaComentarioList: bicicletaComentario.entities,
  loading: bicicletaComentario.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BicicletaComentario);
