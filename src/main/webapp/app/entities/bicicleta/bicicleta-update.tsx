import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, byteSize, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEvento } from 'app/shared/model/evento.model';
import { getEntities as getEventos } from 'app/entities/evento/evento.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './bicicleta.reducer';
import { IBicicleta } from 'app/shared/model/bicicleta.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBicicletaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BicicletaUpdate = (props: IBicicletaUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { bicicletaEntity, eventos, loading, updating } = props;

  const { descricao } = bicicletaEntity;

  const handleClose = () => {
    props.history.push('/bicicleta');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getEventos();
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
    if (errors.length === 0) {
      const entity = {
        ...bicicletaEntity,
        ...values,
        evento: eventos.find(it => it.id.toString() === values.eventoId.toString()),
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
          <h2 id="levaramMinhaBikeApp.bicicleta.home.createOrEditLabel" data-cy="BicicletaCreateUpdateHeading">
            <Translate contentKey="levaramMinhaBikeApp.bicicleta.home.createOrEditLabel">Create or edit a Bicicleta</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : bicicletaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="bicicleta-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="bicicleta-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descricaoLabel" for="bicicleta-descricao">
                  <Translate contentKey="levaramMinhaBikeApp.bicicleta.descricao">Descricao</Translate>
                </Label>
                <AvInput
                  id="bicicleta-descricao"
                  data-cy="descricao"
                  type="textarea"
                  name="descricao"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="bicicleta-status">
                  <Translate contentKey="levaramMinhaBikeApp.bicicleta.status">Status</Translate>
                </Label>
                <AvInput
                  id="bicicleta-status"
                  data-cy="status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && bicicletaEntity.status) || 'PERDIDA'}
                >
                  <option value="PERDIDA">{translate('levaramMinhaBikeApp.StatusBicicleta.PERDIDA')}</option>
                  <option value="ENCONTRADA">{translate('levaramMinhaBikeApp.StatusBicicleta.ENCONTRADA')}</option>
                  <option value="RECUPERADA">{translate('levaramMinhaBikeApp.StatusBicicleta.RECUPERADA')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="numeroQuadroLabel" for="bicicleta-numeroQuadro">
                  <Translate contentKey="levaramMinhaBikeApp.bicicleta.numeroQuadro">Numero Quadro</Translate>
                </Label>
                <AvField
                  id="bicicleta-numeroQuadro"
                  data-cy="numeroQuadro"
                  type="text"
                  name="numeroQuadro"
                  validate={{
                    maxLength: { value: 100, errorMessage: translate('entity.validation.maxlength', { max: 100 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="numeroBikeRegistradaLabel" for="bicicleta-numeroBikeRegistrada">
                  <Translate contentKey="levaramMinhaBikeApp.bicicleta.numeroBikeRegistrada">Numero Bike Registrada</Translate>
                </Label>
                <AvField
                  id="bicicleta-numeroBikeRegistrada"
                  data-cy="numeroBikeRegistrada"
                  type="text"
                  name="numeroBikeRegistrada"
                  validate={{
                    maxLength: { value: 100, errorMessage: translate('entity.validation.maxlength', { max: 100 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="bicicleta-evento">
                  <Translate contentKey="levaramMinhaBikeApp.bicicleta.evento">Evento</Translate>
                </Label>
                <AvInput id="bicicleta-evento" data-cy="evento" type="select" className="form-control" name="eventoId">
                  <option value="" key="0" />
                  {eventos
                    ? eventos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/bicicleta" replace color="info">
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
  eventos: storeState.evento.entities,
  bicicletaEntity: storeState.bicicleta.entity,
  loading: storeState.bicicleta.loading,
  updating: storeState.bicicleta.updating,
  updateSuccess: storeState.bicicleta.updateSuccess,
});

const mapDispatchToProps = {
  getEventos,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BicicletaUpdate);
