import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './usuario-aplicacao.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUsuarioAplicacaoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UsuarioAplicacaoDetail = (props: IUsuarioAplicacaoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { usuarioAplicacaoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="usuarioAplicacaoDetailsHeading">
          <Translate contentKey="levaramMinhaBikeApp.usuarioAplicacao.detail.title">UsuarioAplicacao</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{usuarioAplicacaoEntity.id}</dd>
          <dt>
            <span id="contato">
              <Translate contentKey="levaramMinhaBikeApp.usuarioAplicacao.contato">Contato</Translate>
            </span>
          </dt>
          <dd>{usuarioAplicacaoEntity.contato}</dd>
          <dt>
            <Translate contentKey="levaramMinhaBikeApp.usuarioAplicacao.internalUser">Internal User</Translate>
          </dt>
          <dd>{usuarioAplicacaoEntity.internalUser ? usuarioAplicacaoEntity.internalUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/usuario-aplicacao" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/usuario-aplicacao/${usuarioAplicacaoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ usuarioAplicacao }: IRootState) => ({
  usuarioAplicacaoEntity: usuarioAplicacao.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UsuarioAplicacaoDetail);
