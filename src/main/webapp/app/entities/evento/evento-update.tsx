import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, byteSize, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './evento.reducer';
import { IEvento } from 'app/shared/model/evento.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEventoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EventoUpdate = (props: IEventoUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { eventoEntity, loading, updating } = props;

  const { detalhesLocal, descricaoEvento } = eventoEntity;

  const handleClose = () => {
    props.history.push('/evento' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.dataHoraEvento = convertDateTimeToServer(values.dataHoraEvento);
    values.dataHoraCriacaoRegistro = convertDateTimeToServer(values.dataHoraCriacaoRegistro);

    if (errors.length === 0) {
      const entity = {
        ...eventoEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="levaramMinhaBikeApp.evento.home.createOrEditLabel" data-cy="EventoCreateUpdateHeading">
            <Translate contentKey="levaramMinhaBikeApp.evento.home.createOrEditLabel">Create or edit a Evento</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : eventoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="evento-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="evento-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dataHoraEventoLabel" for="evento-dataHoraEvento">
                  <Translate contentKey="levaramMinhaBikeApp.evento.dataHoraEvento">Data Hora Evento</Translate>
                </Label>
                <AvInput
                  id="evento-dataHoraEvento"
                  data-cy="dataHoraEvento"
                  type="datetime-local"
                  className="form-control"
                  name="dataHoraEvento"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.eventoEntity.dataHoraEvento)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="tipoEventoLabel" for="evento-tipoEvento">
                  <Translate contentKey="levaramMinhaBikeApp.evento.tipoEvento">Tipo Evento</Translate>
                </Label>
                <AvInput
                  id="evento-tipoEvento"
                  data-cy="tipoEvento"
                  type="select"
                  className="form-control"
                  name="tipoEvento"
                  value={(!isNew && eventoEntity.tipoEvento) || 'FURTO'}
                >
                  <option value="FURTO">{translate('levaramMinhaBikeApp.TipoEvento.FURTO')}</option>
                  <option value="ROUBO">{translate('levaramMinhaBikeApp.TipoEvento.ROUBO')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="tipoLocalLabel" for="evento-tipoLocal">
                  <Translate contentKey="levaramMinhaBikeApp.evento.tipoLocal">Tipo Local</Translate>
                </Label>
                <AvInput
                  id="evento-tipoLocal"
                  data-cy="tipoLocal"
                  type="select"
                  className="form-control"
                  name="tipoLocal"
                  value={(!isNew && eventoEntity.tipoLocal) || 'RUA'}
                >
                  <option value="RUA">{translate('levaramMinhaBikeApp.TipoLocal.RUA')}</option>
                  <option value="CALCADA">{translate('levaramMinhaBikeApp.TipoLocal.CALCADA')}</option>
                  <option value="CICLOVIA">{translate('levaramMinhaBikeApp.TipoLocal.CICLOVIA')}</option>
                  <option value="PRACA">{translate('levaramMinhaBikeApp.TipoLocal.PRACA')}</option>
                  <option value="MORADIA">{translate('levaramMinhaBikeApp.TipoLocal.MORADIA')}</option>
                  <option value="CONDOMINIO">{translate('levaramMinhaBikeApp.TipoLocal.CONDOMINIO')}</option>
                  <option value="TRILHA">{translate('levaramMinhaBikeApp.TipoLocal.TRILHA')}</option>
                  <option value="OUTRO">{translate('levaramMinhaBikeApp.TipoLocal.OUTRO')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="coordenadaLocalLabel" for="evento-coordenadaLocal">
                  <Translate contentKey="levaramMinhaBikeApp.evento.coordenadaLocal">Coordenada Local</Translate>
                </Label>
                <AvField
                  id="evento-coordenadaLocal"
                  data-cy="coordenadaLocal"
                  type="text"
                  name="coordenadaLocal"
                  validate={{
                    maxLength: { value: 200, errorMessage: translate('entity.validation.maxlength', { max: 200 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="detalhesLocalLabel" for="evento-detalhesLocal">
                  <Translate contentKey="levaramMinhaBikeApp.evento.detalhesLocal">Detalhes Local</Translate>
                </Label>
                <AvInput id="evento-detalhesLocal" data-cy="detalhesLocal" type="textarea" name="detalhesLocal" />
              </AvGroup>
              <AvGroup>
                <Label id="dataHoraCriacaoRegistroLabel" for="evento-dataHoraCriacaoRegistro">
                  <Translate contentKey="levaramMinhaBikeApp.evento.dataHoraCriacaoRegistro">Data Hora Criacao Registro</Translate>
                </Label>
                <AvInput
                  id="evento-dataHoraCriacaoRegistro"
                  data-cy="dataHoraCriacaoRegistro"
                  type="datetime-local"
                  className="form-control"
                  name="dataHoraCriacaoRegistro"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.eventoEntity.dataHoraCriacaoRegistro)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descricaoEventoLabel" for="evento-descricaoEvento">
                  <Translate contentKey="levaramMinhaBikeApp.evento.descricaoEvento">Descricao Evento</Translate>
                </Label>
                <AvInput id="evento-descricaoEvento" data-cy="descricaoEvento" type="textarea" name="descricaoEvento" />
              </AvGroup>
              <AvGroup>
                <Label id="formaFixacaoLabel" for="evento-formaFixacao">
                  <Translate contentKey="levaramMinhaBikeApp.evento.formaFixacao">Forma Fixacao</Translate>
                </Label>
                <AvInput
                  id="evento-formaFixacao"
                  data-cy="formaFixacao"
                  type="select"
                  className="form-control"
                  name="formaFixacao"
                  value={(!isNew && eventoEntity.formaFixacao) || 'SOLTA'}
                >
                  <option value="SOLTA">{translate('levaramMinhaBikeApp.FormaFixacao.SOLTA')}</option>
                  <option value="TRAVA_EM_U">{translate('levaramMinhaBikeApp.FormaFixacao.TRAVA_EM_U')}</option>
                  <option value="CORRENTE">{translate('levaramMinhaBikeApp.FormaFixacao.CORRENTE')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="metodoCoercaoLabel" for="evento-metodoCoercao">
                  <Translate contentKey="levaramMinhaBikeApp.evento.metodoCoercao">Metodo Coercao</Translate>
                </Label>
                <AvInput
                  id="evento-metodoCoercao"
                  data-cy="metodoCoercao"
                  type="select"
                  className="form-control"
                  name="metodoCoercao"
                  value={(!isNew && eventoEntity.metodoCoercao) || 'ARMA_BRANCA'}
                >
                  <option value="ARMA_BRANCA">{translate('levaramMinhaBikeApp.MetodoCoercao.ARMA_BRANCA')}</option>
                  <option value="ARMA_DE_FOGO">{translate('levaramMinhaBikeApp.MetodoCoercao.ARMA_DE_FOGO')}</option>
                  <option value="AMEACA">{translate('levaramMinhaBikeApp.MetodoCoercao.AMEACA')}</option>
                  <option value="OUTRO">{translate('levaramMinhaBikeApp.MetodoCoercao.OUTRO')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="numEnvolvidosAssaltantesLabel" for="evento-numEnvolvidosAssaltantes">
                  <Translate contentKey="levaramMinhaBikeApp.evento.numEnvolvidosAssaltantes">Num Envolvidos Assaltantes</Translate>
                </Label>
                <AvField
                  id="evento-numEnvolvidosAssaltantes"
                  data-cy="numEnvolvidosAssaltantes"
                  type="string"
                  className="form-control"
                  name="numEnvolvidosAssaltantes"
                  validate={{
                    min: { value: 1, errorMessage: translate('entity.validation.min', { min: 1 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="numEnvolvidosVitimasLabel" for="evento-numEnvolvidosVitimas">
                  <Translate contentKey="levaramMinhaBikeApp.evento.numEnvolvidosVitimas">Num Envolvidos Vitimas</Translate>
                </Label>
                <AvField
                  id="evento-numEnvolvidosVitimas"
                  data-cy="numEnvolvidosVitimas"
                  type="string"
                  className="form-control"
                  name="numEnvolvidosVitimas"
                  validate={{
                    min: { value: 1, errorMessage: translate('entity.validation.min', { min: 1 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/evento" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  eventoEntity: storeState.evento.entity,
  loading: storeState.evento.loading,
  updating: storeState.evento.updating,
  updateSuccess: storeState.evento.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EventoUpdate);
