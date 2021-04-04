import { Drawer, Hidden, List, makeStyles, Toolbar } from '@material-ui/core';
import Box from '@material-ui/core/Box/Box';
import React, { Component } from 'react';
import NavigatorAdmin from './NavigatorAdmin';
import NavigatorGerente from './NavigatorGerente';

const useStyles = makeStyles((theme) => ({
  drawer: {
    width: 240,
    flexShrink: 0,

  },
  drawerPaper: {
    width: 240,
    borderRight: 'none',

  },
}));



interface NavigatorProps {
  perfil: string | undefined;
}

const Navigator: React.FC<NavigatorProps> = (props) => {

  const classes = useStyles();

  const { perfil } = props;

  function CallNavigator() {

    if (perfil === "ADMINISTRADOR") {
      return (
        <Hidden>
          <Drawer
            className={classes.drawer}
            variant='permanent'
            classes={{
              paper: classes.drawerPaper,
            }}
          >
            <Toolbar />
            <div>
              <List>
                <NavigatorAdmin />

                <NavigatorGerente />
              </List>
            </div>
          </Drawer>

        </Hidden>

      )
    } else {
      return (
        <Hidden>
          <Drawer
            className={classes.drawer}
            variant='permanent'
            classes={{
              paper: classes.drawerPaper,
            }}
          >
            <Toolbar />
            <div>
              <List>
                <NavigatorAdmin />

                <NavigatorGerente />
              </List>
            </div>
          </Drawer>

        </Hidden>

      )
    }
  }

  return (
    <CallNavigator />
  )



}

export default Navigator;