import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './usuario-aplicacao.reducer';
import { IUsuarioAplicacao } from 'app/shared/model/usuario-aplicacao.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUsuarioAplicacaoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UsuarioAplicacaoUpdate = (props: IUsuarioAplicacaoUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { usuarioAplicacaoEntity, users, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/usuario-aplicacao');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...usuarioAplicacaoEntity,
        ...values,
        internalUser: users.find(it => it.id.toString() === values.internalUserId.toString()),
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
          <h2 id="levaramMinhaBikeApp.usuarioAplicacao.home.createOrEditLabel" data-cy="UsuarioAplicacaoCreateUpdateHeading">
            <Translate contentKey="levaramMinhaBikeApp.usuarioAplicacao.home.createOrEditLabel">
              Create or edit a UsuarioAplicacao
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : usuarioAplicacaoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="usuario-aplicacao-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="usuario-aplicacao-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="contatoLabel" for="usuario-aplicacao-contato">
                  <Translate contentKey="levaramMinhaBikeApp.usuarioAplicacao.contato">Contato</Translate>
                </Label>
                <AvField
                  id="usuario-aplicacao-contato"
                  data-cy="contato"
                  type="text"
                  name="contato"
                  validate={{
                    maxLength: { value: 100, errorMessage: translate('entity.validation.maxlength', { max: 100 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="usuario-aplicacao-internalUser">
                  <Translate contentKey="levaramMinhaBikeApp.usuarioAplicacao.internalUser">Internal User</Translate>
                </Label>
                <AvInput
                  id="usuario-aplicacao-internalUser"
                  data-cy="internalUser"
                  type="select"
                  className="form-control"
                  name="internalUserId"
                >
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/usuario-aplicacao" replace color="info">
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
  users: storeState.userManagement.users,
  usuarioAplicacaoEntity: storeState.usuarioAplicacao.entity,
  loading: storeState.usuarioAplicacao.loading,
  updating: storeState.usuarioAplicacao.updating,
  updateSuccess: storeState.usuarioAplicacao.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UsuarioAplicacaoUpdate);
