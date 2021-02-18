import React from 'react';
import {
  makeStyles,
  AppBar,
  Toolbar,
  IconButton,
  Button,
} from '@material-ui/core';
import { useTheme } from '@material-ui/core/styles';
import MenuIcon from '@material-ui/icons/Menu';
import AccountCircle from '@material-ui/icons/AccountCircle';

import Apps from '@material-ui/icons/Apps';
import MoreVert from '@material-ui/icons/MoreVert';
import VideoCall from '@material-ui/icons/VideoCall';



const useStyles = makeStyles((theme) => ({
  appBar: {
    boxShadow: 'none',
    zIndex: theme.zIndex.drawer + 1,

  },
  logo: {
    height: 25,
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
}));

interface HeaderProps {
  usuario: string | undefined;
}

const Header: React.FC<HeaderProps> = (props) => {
  const classes = useStyles();
  const theme = useTheme();

  const { usuario } = props;

  return (
    <AppBar color='inherit' className={classes.appBar}>
      <Toolbar>
        <IconButton
          edge='start'
          className={classes.menuIcon}
          aria-label='menu'
        >
          <MenuIcon />
        </IconButton>
        <img
          src={
            theme.palette.type === 'dark'
              ? '/images/branco.png'
              : '/images/preto.png'
          }
          alt='logo'
          className={classes.logo}
        />
        <div className={classes.grow} />

        <IconButton className={classes.icons}>
          <VideoCall />
        </IconButton>
        <IconButton className={classes.icons}>
          <Apps />
        </IconButton>
        <IconButton className={classes.icons}>
          <MoreVert />
        </IconButton>
        <Button
          startIcon={<AccountCircle />}
          variant='outlined'
          color='secondary'
        >
          {usuario}
        </Button>
      </Toolbar>
    </AppBar>
  );
}

export default Header;