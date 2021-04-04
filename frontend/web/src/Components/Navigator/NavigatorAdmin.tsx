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
import GroupIcon from '@material-ui/icons/Group';
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


const NavigatorAdmin: React.FC = () => {
  const classes = useStyles();
 
  return (
   
        
          <React.Fragment >
            <ListItem button classes={{ root: classes.listItem }}>
              <ListItemText
                classes={{
                  primary: classes.listItemText,
                }}
                primary="Configurações"
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
                  primary="Cadastro de empresa"
                >
                </ListItemText>
              </Link>
            </ListItem>

            <ListItem button classes={{ root: classes.listItem }}>
              <Link to="/pdv">
                <ListItemIcon><GroupIcon /> </ListItemIcon>
              </Link>
              <Link to="/pdv">
                <ListItemText
                  classes={{
                    primary: classes.listItemText,
                  }}
                  primary="Cadastro de PDV"
                >
                </ListItemText>
              </Link>
            </ListItem>
            </React.Fragment>
         
    
  );
}

export default NavigatorAdmin;