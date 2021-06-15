import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './bicicleta.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBicicletaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BicicletaDetail = (props: IBicicletaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { bicicletaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bicicletaDetailsHeading">
          <Translate contentKey="levaramMinhaBikeApp.bicicleta.detail.title">Bicicleta</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bicicletaEntity.id}</dd>
          <dt>
            <span id="descricao">
              <Translate contentKey="levaramMinhaBikeApp.bicicleta.descricao">Descricao</Translate>
            </span>
          </dt>
          <dd>{bicicletaEntity.descricao}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="levaramMinhaBikeApp.bicicleta.status">Status</Translate>
            </span>
          </dt>
          <dd>{bicicletaEntity.status}</dd>
          <dt>
            <span id="numeroQuadro">
              <Translate contentKey="levaramMinhaBikeApp.bicicleta.numeroQuadro">Numero Quadro</Translate>
            </span>
          </dt>
          <dd>{bicicletaEntity.numeroQuadro}</dd>
          <dt>
            <span id="numeroBikeRegistrada">
              <Translate contentKey="levaramMinhaBikeApp.bicicleta.numeroBikeRegistrada">Numero Bike Registrada</Translate>
            </span>
          </dt>
          <dd>{bicicletaEntity.numeroBikeRegistrada}</dd>
          <dt>
            <Translate contentKey="levaramMinhaBikeApp.bicicleta.evento">Evento</Translate>
          </dt>
          <dd>{bicicletaEntity.evento ? bicicletaEntity.evento.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bicicleta" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bicicleta/${bicicletaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ bicicleta }: IRootState) => ({
  bicicletaEntity: bicicleta.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BicicletaDetail);
