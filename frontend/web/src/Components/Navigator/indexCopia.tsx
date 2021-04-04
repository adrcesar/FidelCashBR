import React from 'react';
import {
  makeStyles,
  Toolbar,
  Drawer,
  List,
  Divider,
  ListItem,
  ListItemIcon,
  ListItemText,
  Hidden,
} from '@material-ui/core';
import { useTheme } from '@material-ui/core/styles';
import PermMediaOutlinedIcon from '@material-ui/icons/PhotoSizeSelectActual';
import GroupIcon from '@material-ui/icons/Group';
import PeopleIcon from '@material-ui/icons/People';
import { Link } from 'react-router-dom';

const useStyles = makeStyles((theme) => ({
  drawer: {
    width: 240,
    flexShrink: 0,

  },
  drawerPaper: {
    width: 240,
    borderRight: 'none',

  },
  listItemText: {
    fontSize: 14,
  },
  listItem: {
    paddingTop: 4,
    paddingBottom: 4,
  },
  subheader: {
    textTransform: 'uppercase',
  },
}));





interface ChildrenProps {
  id: string,
  icon: JSX.Element,
  linkTo?:  string,
  active?: boolean
}

interface CategoriaProps {
  id: string,
  children: ChildrenProps[]
}

let categories: CategoriaProps[] = [];




const perfilAdmin: CategoriaProps = {
  id: 'Configurações',
  children: [
    { id: 'Cadastro de empresa', icon: <PermMediaOutlinedIcon />, linkTo: '/'/* active: true */ },
    { id: 'Cadastro de PDV', icon: <GroupIcon />, linkTo: '/pdv' /* active: true */ },
  ],
};

const perfilOperador: CategoriaProps = {
  id: 'Gerenciamento',
  children: [
    { id: 'Campanhas', icon: <PeopleIcon /> },
  ],
};

function createMenuByPerfil(perfil: string | undefined) {
  if (perfil === "ADMINISTRADOR") {
    categories = [];
    categories.push(perfilAdmin);
    categories.push(perfilOperador);
  }
}

interface NavigatorProps {
  perfil: string | undefined;
}

const Navigator1: React.FC<NavigatorProps> = (props) => {
  const classes = useStyles();
  const theme = useTheme();

  const { perfil } = props;

  createMenuByPerfil(perfil);

  return (
    <Hidden mdDown>
      <Drawer
        className={classes.drawer}
        variant='permanent'
        classes={{
          paper: classes.drawerPaper,
        }}
      >
        <Toolbar />
        <div >
          <List>
            {categories.map(({ id, children }) => (
              <React.Fragment key={id}>
                <ListItem button classes={{ root: classes.listItem }}>
                  <ListItemText
                    classes={{
                      primary: classes.listItemText,
                    }}
                    primary={id}
                  />
                </ListItem>
                {children.map(({ id: childId, icon, linkTo, active }) =>
                  <ListItem button classes={{ root: classes.listItem }}>
                    
                    <Link to={{
                                pathname: "/pdv"
                     }}> 
                      <ListItemIcon>{icon}</ListItemIcon>
                      <ListItemText
                        classes={{
                          primary: classes.listItemText,
                        }}
                        primary={childId}
                      >
                      </ListItemText>

                     </Link>
                   

                  </ListItem>
                )}
                <Divider />
              </React.Fragment>
            ))}
            <ListItem>
              <Link to="/importacao">
                <ListItemText primary="teste" />
              </Link>


            </ListItem>
          </List>
        </div>
      </Drawer>
    </Hidden>

  );
}

export default Navigator1;