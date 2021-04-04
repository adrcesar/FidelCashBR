import React from 'react';
import {
  makeStyles,
  Toolbar,
  Drawer,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  Hidden,
} from '@material-ui/core';

import PermMediaOutlinedIcon from '@material-ui/icons/PhotoSizeSelectActual';
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


const NavigatorGerente: React.FC = () => {
  const classes = useStyles();

  return (

    <React.Fragment >
      <ListItem button classes={{ root: classes.listItem }}>
        <ListItemText
          classes={{
            primary: classes.listItemText,
          }}
          primary="Relatórios"
        />

      </ListItem>
      <ListItem button classes={{ root: classes.listItem }}>
        <Link to="/">
          <ListItemIcon><PermMediaOutlinedIcon /></ListItemIcon>
        </Link>
        <Link to="/">
          <ListItemText
            classes={{
              primary: classes.listItemText,
            }}
            primary="Movimentação de cupons fiscais"
          >
          </ListItemText>
        </Link>
      </ListItem>
    </React.Fragment>


  );
}

export default NavigatorGerente;