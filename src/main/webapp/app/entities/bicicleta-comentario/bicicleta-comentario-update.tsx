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
import { getEntity, updateEntity, createEntity, reset } from './bicicleta-comentario.reducer';
import { IBicicletaComentario } from 'app/shared/model/bicicleta-comentario.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBicicletaComentarioUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BicicletaComentarioUpdate = (props: IBicicletaComentarioUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { bicicletaComentarioEntity, bicicletas, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/bicicleta-comentario');
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
        ...bicicletaComentarioEntity,
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
          <h2 id="levaramMinhaBikeApp.bicicletaComentario.home.createOrEditLabel" data-cy="BicicletaComentarioCreateUpdateHeading">
            <Translate contentKey="levaramMinhaBikeApp.bicicletaComentario.home.createOrEditLabel">
              Create or edit a BicicletaComentario
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : bicicletaComentarioEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="bicicleta-comentario-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="bicicleta-comentario-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="observacaoLabel" for="bicicleta-comentario-observacao">
                  <Translate contentKey="levaramMinhaBikeApp.bicicletaComentario.observacao">Observacao</Translate>
                </Label>
                <AvField id="bicicleta-comentario-observacao" data-cy="observacao" type="text" name="observacao" />
              </AvGroup>
              <AvGroup>
                <Label for="bicicleta-comentario-bicicleta">
                  <Translate contentKey="levaramMinhaBikeApp.bicicletaComentario.bicicleta">Bicicleta</Translate>
                </Label>
                <AvInput id="bicicleta-comentario-bicicleta" data-cy="bicicleta" type="select" className="form-control" name="bicicletaId">
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
              <Button tag={Link} id="cancel-save" to="/bicicleta-comentario" replace color="info">
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
  bicicletaComentarioEntity: storeState.bicicletaComentario.entity,
  loading: storeState.bicicletaComentario.loading,
  updating: storeState.bicicletaComentario.updating,
  updateSuccess: storeState.bicicletaComentario.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(BicicletaComentarioUpdate);
