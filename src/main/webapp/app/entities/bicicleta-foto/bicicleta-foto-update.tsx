import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBicicleta } from 'app/shared/model/bicicleta.model';
import { getEntities as getBicicletas } from 'app/entities/bicicleta/bicicleta.reducer';
import { getEntity, updateEntity, createEntity, reset } from './bicicleta-foto.reducer';
import { IBicicletaFoto } from 'app/shared/model/bicicleta-foto.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBicicletaFotoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BicicletaFotoUpdate = (props: IBicicletaFotoUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { bicicletaFotoEntity, bicicletas, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/bicicleta-foto');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getBicicletas();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...bicicletaFotoEntity,
        ...values,
        bicicleta: bicicletas.find(it => it.id.toString() === values.bicicletaId.toString()),
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
          <h2 id="levaramMinhaBikeApp.bicicletaFoto.home.createOrEditLabel" data-cy="BicicletaFotoCreateUpdateHeading">
            <Translate contentKey="levaramMinhaBikeApp.bicicletaFoto.home.createOrEditLabel">Create or edit a BicicletaFoto</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : bicicletaFotoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="bicicleta-foto-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="bicicleta-foto-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="urlImagemLabel" for="bicicleta-foto-urlImagem">
                  <Translate contentKey="levaramMinhaBikeApp.bicicletaFoto.urlImagem">Url Imagem</Translate>
                </Label>
                <AvField
                  id="bicicleta-foto-urlImagem"
                  data-cy="urlImagem"
                  type="text"
                  name="urlImagem"
                  validate={{
                    maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="bicicleta-foto-bicicleta">
                  <Translate contentKey="levaramMinhaBikeApp.bicicletaFoto.bicicleta">Bicicleta</Translate>
                </Label>
                <AvInput id="bicicleta-foto-bicicleta" data-cy="bicicleta" type="select" className="form-control" name="bicicletaId">
                  <option value="" key="0" />
                  {bicicletas
                    ? bicicletas.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/bicicleta-foto" replace color="info">
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
  bicicletas: storeState.bicicleta.entities,
  bicicletaFotoEntity: storeState.bicicletaFoto.entity,
  loading: storeState.bicicletaFoto.loading,
  updating: storeState.bicicletaFoto.updating,
  updateSuccess: storeState.bicicletaFoto.updateSuccess,
});

const mapDispatchToProps = {
  getBicicletas,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BicicletaFotoUpdate);
