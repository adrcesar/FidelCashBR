import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Navigator from '../../Components/Navigator';
import Header from '../../Components/Header';

import { useAuth } from "../../contexts/auth";
import { Box } from '@material-ui/core';
import FormImplantacao from './formImplantacao';

const useStyles = makeStyles((theme) => ({
  root: {
    height: '100vh',
    /* backgroundColor: theme.palette.background.paper, */
  },
  content: {
    width: '100vw',
    height: '90vh',

  },
}));

function DashBoard() {
  const classes = useStyles();
  const { user, signOut } = useAuth();

  return (

    <Box className={classes.root} p={8}>
      <Header usuario={user?.name} />
      <Box display='flex'>
        <Navigator perfil={user?.perfil} />
        <Box bgcolor='#eeeeee' className={classes.content} >
          <FormImplantacao />
        </Box>
      </Box>
    </Box> 
  )}

export default DashBoard;