import React from 'react';
import SnackBar from '@material-ui/core/Snackbar';
import Alert, { AlertProps } from '@material-ui/lab/Alert'; //deve instalar - npm install @material-ui/lab
import { VerticalAlignCenterOutlined } from '@material-ui/icons';

interface ToastProps {
    open: boolean;
    handleClose(): any;
    children: string;
    severity: any;
}

const vertical = 'top';
const horizontal = 'center';

const Toast: React.FC<ToastProps> = (props) => {
    return (
        <SnackBar
            anchorOrigin={{ vertical, horizontal }}
            open={props.open}
            onClose={props.handleClose}
            autoHideDuration={3000}
            key={vertical + horizontal}
            
        >
            <Alert onClose={props.handleClose} variant='filled' severity={props.severity}>
                {props.children}
                <br></br>
            </Alert>
        </SnackBar>
    )
}

export default Toast;