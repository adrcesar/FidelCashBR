import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Navigator from '../../Components/Navigator';
import Header from '../../Components/Header';

import { useAuth } from "../../contexts/auth";
import { Box } from '@material-ui/core';
import SignIn from '../SignIn';
import FormImplantacao from './formImplantacao';

const useStyles = makeStyles((theme) => ({
  root: {
    height: '100vh',
    backgroundColor: theme.palette.background.paper,
  },
  content: {
    width: '100vw'
  },
}));

function DashBoard() {
  const classes = useStyles();
  const { user, signOut } = useAuth();

  function handleSignOut() {
    signOut();
  }

  return (
    <div className={classes.root}>
      <Header usuario={user?.name} />
      <Box display='flex'>
        <Navigator perfil={user?.perfil} />
        <Box className={classes.content} p={10}>
          <h1>Logado</h1>
          <p>{user?.name}</p>
          <button title="Sign Out" onClick={handleSignOut} />
          <FormImplantacao />
        </Box>
      </Box>
    </div>
  );
}

export default DashBoard;