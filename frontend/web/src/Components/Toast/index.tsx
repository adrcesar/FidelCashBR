import React from 'react';
import SnackBar from '@material-ui/core/Snackbar';
import Alert, { AlertProps } from '@material-ui/lab/Alert'; //deve instalar - npm install @material-ui/lab
import { makeStyles } from '@material-ui/core/styles';
import { Box, Container, createStyles } from '@material-ui/core';

const useStyles = makeStyles((theme) =>
    createStyles({
        root: {
            width: '100%',
            /* '& > * + *': {
                marginTop: theme.spacing(2),

            }, */


        },
    }),
);

/* '& > * + *': {
    marginTop: theme.spacing(0),
}, */

interface ToastProps {
    open: boolean;
    handleClose(): any;
    children: string;
    severity: any;
}

const vertical = 'top';
const horizontal = 'center';

const Toast: React.FC<ToastProps> = (props) => {
    const classes = useStyles();
    return (

        <SnackBar 
            className={classes.root}
            anchorOrigin={{ vertical, horizontal }}
            open={props.open}
            onClose={props.handleClose}
            autoHideDuration={3000}
            key={vertical + horizontal}

        >

            <Alert onClose={props.handleClose} variant='filled' severity={props.severity} className={classes.root}>
                {props.children}
            </Alert>


        </SnackBar>



    )
}

export default Toast;