import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './evento.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEventoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EventoDetail = (props: IEventoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { eventoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="eventoDetailsHeading">
          <Translate contentKey="levaramMinhaBikeApp.evento.detail.title">Evento</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{eventoEntity.id}</dd>
          <dt>
            <span id="dataHoraEvento">
              <Translate contentKey="levaramMinhaBikeApp.evento.dataHoraEvento">Data Hora Evento</Translate>
            </span>
          </dt>
          <dd>
            {eventoEntity.dataHoraEvento ? <TextFormat value={eventoEntity.dataHoraEvento} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="tipoEvento">
              <Translate contentKey="levaramMinhaBikeApp.evento.tipoEvento">Tipo Evento</Translate>
            </span>
          </dt>
          <dd>{eventoEntity.tipoEvento}</dd>
          <dt>
            <span id="tipoLocal">
              <Translate contentKey="levaramMinhaBikeApp.evento.tipoLocal">Tipo Local</Translate>
            </span>
          </dt>
          <dd>{eventoEntity.tipoLocal}</dd>
          <dt>
            <span id="coordenadaLocal">
              <Translate contentKey="levaramMinhaBikeApp.evento.coordenadaLocal">Coordenada Local</Translate>
            </span>
          </dt>
          <dd>{eventoEntity.coordenadaLocal}</dd>
          <dt>
            <span id="detalhesLocal">
              <Translate contentKey="levaramMinhaBikeApp.evento.detalhesLocal">Detalhes Local</Translate>
            </span>
          </dt>
          <dd>{eventoEntity.detalhesLocal}</dd>
          <dt>
            <span id="dataHoraCriacaoRegistro">
              <Translate contentKey="levaramMinhaBikeApp.evento.dataHoraCriacaoRegistro">Data Hora Criacao Registro</Translate>
            </span>
          </dt>
          <dd>
            {eventoEntity.dataHoraCriacaoRegistro ? (
              <TextFormat value={eventoEntity.dataHoraCriacaoRegistro} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="descricaoEvento">
              <Translate contentKey="levaramMinhaBikeApp.evento.descricaoEvento">Descricao Evento</Translate>
            </span>
          </dt>
          <dd>{eventoEntity.descricaoEvento}</dd>
          <dt>
            <span id="formaFixacao">
              <Translate contentKey="levaramMinhaBikeApp.evento.formaFixacao">Forma Fixacao</Translate>
            </span>
          </dt>
          <dd>{eventoEntity.formaFixacao}</dd>
          <dt>
            <span id="metodoCoercao">
              <Translate contentKey="levaramMinhaBikeApp.evento.metodoCoercao">Metodo Coercao</Translate>
            </span>
          </dt>
          <dd>{eventoEntity.metodoCoercao}</dd>
          <dt>
            <span id="numEnvolvidosAssaltantes">
              <Translate contentKey="levaramMinhaBikeApp.evento.numEnvolvidosAssaltantes">Num Envolvidos Assaltantes</Translate>
            </span>
          </dt>
          <dd>{eventoEntity.numEnvolvidosAssaltantes}</dd>
          <dt>
            <span id="numEnvolvidosVitimas">
              <Translate contentKey="levaramMinhaBikeApp.evento.numEnvolvidosVitimas">Num Envolvidos Vitimas</Translate>
            </span>
          </dt>
          <dd>{eventoEntity.numEnvolvidosVitimas}</dd>
        </dl>
        <Button tag={Link} to="/evento" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/evento/${eventoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ evento }: IRootState) => ({
  eventoEntity: evento.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EventoDetail);
