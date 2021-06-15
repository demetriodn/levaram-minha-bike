import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './bicicleta-comentario.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBicicletaComentarioDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BicicletaComentarioDetail = (props: IBicicletaComentarioDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { bicicletaComentarioEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bicicletaComentarioDetailsHeading">
          <Translate contentKey="levaramMinhaBikeApp.bicicletaComentario.detail.title">BicicletaComentario</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bicicletaComentarioEntity.id}</dd>
          <dt>
            <span id="observacao">
              <Translate contentKey="levaramMinhaBikeApp.bicicletaComentario.observacao">Observacao</Translate>
            </span>
          </dt>
          <dd>{bicicletaComentarioEntity.observacao}</dd>
          <dt>
            <Translate contentKey="levaramMinhaBikeApp.bicicletaComentario.bicicleta">Bicicleta</Translate>
          </dt>
          <dd>{bicicletaComentarioEntity.bicicleta ? bicicletaComentarioEntity.bicicleta.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bicicleta-comentario" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bicicleta-comentario/${bicicletaComentarioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ bicicletaComentario }: IRootState) => ({
  bicicletaComentarioEntity: bicicletaComentario.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BicicletaComentarioDetail);
