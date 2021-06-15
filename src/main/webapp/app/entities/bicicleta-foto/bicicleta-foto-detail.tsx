import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './bicicleta-foto.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBicicletaFotoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BicicletaFotoDetail = (props: IBicicletaFotoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { bicicletaFotoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bicicletaFotoDetailsHeading">
          <Translate contentKey="levaramMinhaBikeApp.bicicletaFoto.detail.title">BicicletaFoto</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bicicletaFotoEntity.id}</dd>
          <dt>
            <span id="urlImagem">
              <Translate contentKey="levaramMinhaBikeApp.bicicletaFoto.urlImagem">Url Imagem</Translate>
            </span>
          </dt>
          <dd>{bicicletaFotoEntity.urlImagem}</dd>
          <dt>
            <Translate contentKey="levaramMinhaBikeApp.bicicletaFoto.bicicleta">Bicicleta</Translate>
          </dt>
          <dd>{bicicletaFotoEntity.bicicleta ? bicicletaFotoEntity.bicicleta.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bicicleta-foto" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bicicleta-foto/${bicicletaFotoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ bicicletaFoto }: IRootState) => ({
  bicicletaFotoEntity: bicicletaFoto.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BicicletaFotoDetail);
