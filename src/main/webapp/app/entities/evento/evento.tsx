import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './evento.reducer';
import { IEvento } from 'app/shared/model/evento.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IEventoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Evento = (props: IEventoProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { eventoList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="evento-heading" data-cy="EventoHeading">
        <Translate contentKey="levaramMinhaBikeApp.evento.home.title">Eventos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="levaramMinhaBikeApp.evento.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="levaramMinhaBikeApp.evento.home.createLabel">Create new Evento</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {eventoList && eventoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="levaramMinhaBikeApp.evento.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dataHoraEvento')}>
                  <Translate contentKey="levaramMinhaBikeApp.evento.dataHoraEvento">Data Hora Evento</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tipoEvento')}>
                  <Translate contentKey="levaramMinhaBikeApp.evento.tipoEvento">Tipo Evento</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tipoLocal')}>
                  <Translate contentKey="levaramMinhaBikeApp.evento.tipoLocal">Tipo Local</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('coordenadaLocal')}>
                  <Translate contentKey="levaramMinhaBikeApp.evento.coordenadaLocal">Coordenada Local</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('detalhesLocal')}>
                  <Translate contentKey="levaramMinhaBikeApp.evento.detalhesLocal">Detalhes Local</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dataHoraCriacaoRegistro')}>
                  <Translate contentKey="levaramMinhaBikeApp.evento.dataHoraCriacaoRegistro">Data Hora Criacao Registro</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('descricaoEvento')}>
                  <Translate contentKey="levaramMinhaBikeApp.evento.descricaoEvento">Descricao Evento</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('formaFixacao')}>
                  <Translate contentKey="levaramMinhaBikeApp.evento.formaFixacao">Forma Fixacao</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('metodoCoercao')}>
                  <Translate contentKey="levaramMinhaBikeApp.evento.metodoCoercao">Metodo Coercao</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('numEnvolvidosAssaltantes')}>
                  <Translate contentKey="levaramMinhaBikeApp.evento.numEnvolvidosAssaltantes">Num Envolvidos Assaltantes</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('numEnvolvidosVitimas')}>
                  <Translate contentKey="levaramMinhaBikeApp.evento.numEnvolvidosVitimas">Num Envolvidos Vitimas</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {eventoList.map((evento, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${evento.id}`} color="link" size="sm">
                      {evento.id}
                    </Button>
                  </td>
                  <td>
                    {evento.dataHoraEvento ? <TextFormat type="date" value={evento.dataHoraEvento} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    <Translate contentKey={`levaramMinhaBikeApp.TipoEvento.${evento.tipoEvento}`} />
                  </td>
                  <td>
                    <Translate contentKey={`levaramMinhaBikeApp.TipoLocal.${evento.tipoLocal}`} />
                  </td>
                  <td>{evento.coordenadaLocal}</td>
                  <td>{evento.detalhesLocal}</td>
                  <td>
                    {evento.dataHoraCriacaoRegistro ? (
                      <TextFormat type="date" value={evento.dataHoraCriacaoRegistro} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{evento.descricaoEvento}</td>
                  <td>
                    <Translate contentKey={`levaramMinhaBikeApp.FormaFixacao.${evento.formaFixacao}`} />
                  </td>
                  <td>
                    <Translate contentKey={`levaramMinhaBikeApp.MetodoCoercao.${evento.metodoCoercao}`} />
                  </td>
                  <td>{evento.numEnvolvidosAssaltantes}</td>
                  <td>{evento.numEnvolvidosVitimas}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${evento.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${evento.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${evento.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="levaramMinhaBikeApp.evento.home.notFound">No Eventos found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={eventoList && eventoList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ evento }: IRootState) => ({
  eventoList: evento.entities,
  loading: evento.loading,
  totalItems: evento.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Evento);
