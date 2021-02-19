import React from 'react';
import {
  createMuiTheme,
  createStyles,
  makeStyles,
  ThemeProvider,
  withStyles,
  WithStyles,
} from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import Hidden from '@material-ui/core/Hidden';
import Typography from '@material-ui/core/Typography';
import Link from '@material-ui/core/Link';
import Navigator from '../../Components/Navigator';
import Header from '../../Components/Header';

import { useAuth } from "../../contexts/auth";
import Container from '@material-ui/core/Container';
import Toast from '../../Components/Toast';
import Avatar from '@material-ui/core/Avatar';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import TextField from '@material-ui/core/TextField';
import { Box } from '@material-ui/core';

const useStyles = makeStyles((theme) => ({
  root: {
    height: '100vh',
    backgroundColor: theme.palette.background.default,
  },
  teste: {
    left: 245,
  },
  appBar: {
    boxShadow: 'none',
    zIndex: theme.zIndex.drawer + 1,
  },
  logo: {
    height: 25,
  },
  drawer: {
    width: 240,
    flexShrink: 0,
  },
  drawerPaper: {
    width: 240,
    borderRight: 'none',
  },
  menuIcon: {
    paddingRight: theme.spacing(5),
    paddingLeft: theme.spacing(6),
  },
  icons: {
    paddingRight: theme.spacing(5),
  },
  grow: {
    flexGrow: 1,
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
  paper: {
    marginTop: theme.spacing(8),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing(1),
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
        <Box p={8}>
          <h1>Logado</h1>
          {user?.name}
          <button title="Sign Out" onClick={handleSignOut} />

          <Container component="main" maxWidth="xs">
            <CssBaseline />



            <div className={classes.paper}>

              <Avatar className={classes.avatar}>
                <LockOutlinedIcon />
              </Avatar>
              <Typography component="h1" variant="h5">
                Sign in
                                </Typography>
              <form className={classes.form} noValidate>
                <TextField
                  variant="outlined"
                  margin="normal"
                  required
                  fullWidth
                  id="email"
                  label="Usuário"
                  name="usuario"
                  placeholder="usuário"
                  // onChange={(e) => { setUsuario(e.target.value) }}
                  autoFocus
                />
                <TextField
                  variant="outlined"
                  margin="normal"
                  required
                  fullWidth
                  name="password"
                  label="Password"
                  type="password"
                  id="password"
                  //  onChange={(e) => { setSenha(e.target.value) }}
                  autoComplete="current-password"
                />



              </form>
            </div>

          </Container>
        </Box>

      </Box>
    </div>




  );
}

export default DashBoard;