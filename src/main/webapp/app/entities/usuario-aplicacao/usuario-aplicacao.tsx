import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './usuario-aplicacao.reducer';
import { IUsuarioAplicacao } from 'app/shared/model/usuario-aplicacao.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUsuarioAplicacaoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const UsuarioAplicacao = (props: IUsuarioAplicacaoProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { usuarioAplicacaoList, match, loading } = props;
  return (
    <div>
      <h2 id="usuario-aplicacao-heading" data-cy="UsuarioAplicacaoHeading">
        <Translate contentKey="levaramMinhaBikeApp.usuarioAplicacao.home.title">Usuario Aplicacaos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="levaramMinhaBikeApp.usuarioAplicacao.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="levaramMinhaBikeApp.usuarioAplicacao.home.createLabel">Create new Usuario Aplicacao</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {usuarioAplicacaoList && usuarioAplicacaoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="levaramMinhaBikeApp.usuarioAplicacao.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="levaramMinhaBikeApp.usuarioAplicacao.contato">Contato</Translate>
                </th>
                <th>
                  <Translate contentKey="levaramMinhaBikeApp.usuarioAplicacao.internalUser">Internal User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {usuarioAplicacaoList.map((usuarioAplicacao, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${usuarioAplicacao.id}`} color="link" size="sm">
                      {usuarioAplicacao.id}
                    </Button>
                  </td>
                  <td>{usuarioAplicacao.contato}</td>
                  <td>{usuarioAplicacao.internalUser ? usuarioAplicacao.internalUser.id : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${usuarioAplicacao.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${usuarioAplicacao.id}/edit`}
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
                        to={`${match.url}/${usuarioAplicacao.id}/delete`}
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
              <Translate contentKey="levaramMinhaBikeApp.usuarioAplicacao.home.notFound">No Usuario Aplicacaos found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ usuarioAplicacao }: IRootState) => ({
  usuarioAplicacaoList: usuarioAplicacao.entities,
  loading: usuarioAplicacao.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UsuarioAplicacao);
