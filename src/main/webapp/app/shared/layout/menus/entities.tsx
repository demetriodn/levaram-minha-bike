import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/usuario-aplicacao">
      <Translate contentKey="global.menu.entities.usuarioAplicacao" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/evento">
      <Translate contentKey="global.menu.entities.evento" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bicicleta">
      <Translate contentKey="global.menu.entities.bicicleta" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bicicleta-foto">
      <Translate contentKey="global.menu.entities.bicicletaFoto" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bicicleta-comentario">
      <Translate contentKey="global.menu.entities.bicicletaComentario" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
