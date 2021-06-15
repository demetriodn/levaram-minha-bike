import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './bicicleta-foto.reducer';
import { IBicicletaFoto } from 'app/shared/model/bicicleta-foto.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBicicletaFotoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const BicicletaFoto = (props: IBicicletaFotoProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { bicicletaFotoList, match, loading } = props;
  return (
    <div>
      <h2 id="bicicleta-foto-heading" data-cy="BicicletaFotoHeading">
        <Translate contentKey="levaramMinhaBikeApp.bicicletaFoto.home.title">Bicicleta Fotos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="levaramMinhaBikeApp.bicicletaFoto.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="levaramMinhaBikeApp.bicicletaFoto.home.createLabel">Create new Bicicleta Foto</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bicicletaFotoList && bicicletaFotoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="levaramMinhaBikeApp.bicicletaFoto.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="levaramMinhaBikeApp.bicicletaFoto.urlImagem">Url Imagem</Translate>
                </th>
                <th>
                  <Translate contentKey="levaramMinhaBikeApp.bicicletaFoto.bicicleta">Bicicleta</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bicicletaFotoList.map((bicicletaFoto, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${bicicletaFoto.id}`} color="link" size="sm">
                      {bicicletaFoto.id}
                    </Button>
                  </td>
                  <td>{bicicletaFoto.urlImagem}</td>
                  <td>
                    {bicicletaFoto.bicicleta ? (
                      <Link to={`bicicleta/${bicicletaFoto.bicicleta.id}`}>{bicicletaFoto.bicicleta.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${bicicletaFoto.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bicicletaFoto.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${bicicletaFoto.id}/delete`}
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
              <Translate contentKey="levaramMinhaBikeApp.bicicletaFoto.home.notFound">No Bicicleta Fotos found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ bicicletaFoto }: IRootState) => ({
  bicicletaFotoList: bicicletaFoto.entities,
  loading: bicicletaFoto.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BicicletaFoto);
