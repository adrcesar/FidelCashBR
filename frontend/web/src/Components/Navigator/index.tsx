
import React, { useState } from 'react';
import clsx from 'clsx';
import { createStyles, Theme, withStyles, WithStyles } from '@material-ui/core/styles';
import Divider from '@material-ui/core/Divider';
import Drawer, { DrawerProps } from '@material-ui/core/Drawer';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import HomeIcon from '@material-ui/icons/Home';
import PeopleIcon from '@material-ui/icons/People';
import DnsRoundedIcon from '@material-ui/icons/DnsRounded';
import PermMediaOutlinedIcon from '@material-ui/icons/PhotoSizeSelectActual';
import PublicIcon from '@material-ui/icons/Public';
import SettingsEthernetIcon from '@material-ui/icons/SettingsEthernet';
import SettingsInputComponentIcon from '@material-ui/icons/SettingsInputComponent';
import TimerIcon from '@material-ui/icons/Timer';
import SettingsIcon from '@material-ui/icons/Settings';
import PhonelinkSetupIcon from '@material-ui/icons/PhonelinkSetup';
import { Omit } from '@material-ui/types';

 /* const categories = [
{
  id: 'Develop',
  children: [
    { id: 'Authentication', icon: <PeopleIcon />, active: true },
    { id: 'Database', icon: <DnsRoundedIcon /> },
    { id: 'Storage', icon: <PermMediaOutlinedIcon /> },
    { id: 'Hosting', icon: <PublicIcon /> },
    { id: 'Functions', icon: <SettingsEthernetIcon /> },
    { id: 'ML Kit', icon: <SettingsInputComponentIcon /> },
  ],
},
{
  id: 'Quality',
  children: [
    { id: 'Analytics', icon: <SettingsIcon /> },
    { id: 'Performance', icon: <TimerIcon /> },
    { id: 'Test Lab', icon: <PhonelinkSetupIcon /> },
  ],
},
];*/    

interface ChildrenProps {
  id: string,
  icon: JSX.Element, 
  active?: boolean
}

interface CategoriaProps {
  id: string,
  children: ChildrenProps[]
}  

let categories: CategoriaProps[] = [];


const perfilAdmin: CategoriaProps = {
  id: 'Administrador',
    children: [
      { id: 'Nova Empresa', icon: <PermMediaOutlinedIcon />, active: true }, 
    ], 
};

const perfilOperador: CategoriaProps = {
  id: 'Cliente',
    children: [
      { id: 'Atendimento', icon: <PeopleIcon /> }, 
    ],   
};

function createMenuByPerfil(perfil: string | undefined) {
  if (perfil === "ADMINISTRADOR") {
    console.log("oiiiiii88888888");
    console.log(perfilAdmin);
    console.log(perfilOperador);
    
    categories.push(perfilAdmin);
    console.log("oiiiii9999999");
    console.log(categories);
    categories.push(perfilOperador);
    console.log(categories);
    
    
  }
  
}




const styles = (theme: Theme) =>
  createStyles({
    categoryHeader: {
      paddingTop: theme.spacing(2),
      paddingBottom: theme.spacing(2),
    },
    categoryHeaderPrimary: {
      color: theme.palette.common.white,
    },
    item: {
      paddingTop: 1,
      paddingBottom: 1,
      color: 'rgba(255, 255, 255, 0.7)',
      '&:hover,&:focus': {
        backgroundColor: 'rgba(255, 255, 255, 0.08)',
      },
    },
    itemCategory: {
      backgroundColor: '#232f3e',
      boxShadow: '0 -1px 0 #404854 inset',
      paddingTop: theme.spacing(2),
      paddingBottom: theme.spacing(2),
    },
    firebase: {
      fontSize: 24,
      color: theme.palette.common.white,
    },
    itemActiveItem: {
      color: '#4fc3f7',
    },
    itemPrimary: {
      fontSize: 'inherit',
    },
    itemIcon: {
      minWidth: 'auto',
      marginRight: theme.spacing(2),
    },
    divider: {
      marginTop: theme.spacing(2),
    },
  });

export interface NavigatorProps extends Omit<DrawerProps, 'classes'>, WithStyles<typeof styles> { perfil: string | undefined; }

function Navigator(props: NavigatorProps) {
  const { classes, perfil, ...other } = props;

  createMenuByPerfil(perfil);

  


  return (
    <Drawer variant="permanent" {...other}>
      <List disablePadding>
        <ListItem className={clsx(classes.firebase, classes.item, classes.itemCategory)}>
          {/*  Paperbase */} {perfil}
        </ListItem>
        <ListItem className={clsx(classes.item, classes.itemCategory)}>
          <ListItemIcon className={classes.itemIcon}>
            <HomeIcon />
          </ListItemIcon>
          <ListItemText
            classes={{
              primary: classes.itemPrimary,
            }}
          >
            Project Overview
          </ListItemText>
        </ListItem>
        {categories.map(({ id, children }) => (
          <React.Fragment key={id}>
            <ListItem className={classes.categoryHeader}>
              <ListItemText
                classes={{
                  primary: classes.categoryHeaderPrimary,
                }}
              >
                {id}
              </ListItemText>
            </ListItem>
            {children.map(({ id: childId, icon, active }) => (
              <ListItem
                key={childId}
                button
                className={clsx(classes.item, active && classes.itemActiveItem)}
              >
                <ListItemIcon className={classes.itemIcon}>{icon}</ListItemIcon>
                <ListItemText
                  classes={{
                    primary: classes.itemPrimary,
                  }}
                >
                  {childId}
                </ListItemText>
              </ListItem>
            ))}
            <Divider className={classes.divider} />
          </React.Fragment>
        ))}
      </List>
    </Drawer>
  );
}

export default withStyles(styles)(Navigator);