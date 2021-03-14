import React, { useState } from 'react';
import {
  makeStyles,
  AppBar,
  Toolbar,
  IconButton,
  Menu,
  MenuItem,
  Button,
  Grid,
} from '@material-ui/core';
import { useTheme } from '@material-ui/core/styles';
import MenuIcon from '@material-ui/icons/Menu';
import AccountCircle from '@material-ui/icons/AccountCircle';

import Apps from '@material-ui/icons/Apps';
import MoreVert from '@material-ui/icons/MoreVert';
import VideoCall from '@material-ui/icons/VideoCall';
import { useAuth } from "../../contexts/auth";




const useStyles = makeStyles((theme) => ({
  appBar: {
    boxShadow: 'none',
    zIndex: theme.zIndex.drawer + 1, 
    background: 'theme.palette.background.default',
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
  menuItem: {
    fontWeight: 50,
  }
}));

interface HeaderProps {
  usuario: string | undefined;
}

const Header: React.FC<HeaderProps> = (props) => {
  const classes = useStyles();
  const theme = useTheme();

  const { signOut } = useAuth();

  const [anchorEl, setAnchorEl] = useState(null);

  const handleClick = (event: any) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
    signOut();
  };

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
          aria-controls="simple-menu"
          aria-haspopup="true"
          onClick={handleClick}
          startIcon={<AccountCircle />}
          variant='outlined'
          color='primary'
        >
          {usuario}
        </Button>
        <Menu
          id="simple-menu"
          anchorEl={anchorEl}
          keepMounted
          open={Boolean(anchorEl)}
          onClose={handleClose}
        >
          <MenuItem onClick={handleClose} className={classes.appBar} >Logout  </MenuItem>
        </Menu>
      </Toolbar>
    </AppBar>

  );
}

export default Header;