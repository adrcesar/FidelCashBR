import React from 'react';
import {
    createMuiTheme,
    createStyles,
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

let theme = createMuiTheme({
    palette: {
        primary: {
            light: '#63ccff',
            main: '#009be5',
            dark: '#006db3',
        },
    },
    typography: {
        h5: {
            fontWeight: 500,
            fontSize: 26,
            letterSpacing: 0.5,
        },
    },
    shape: {
        borderRadius: 8,
    },
    props: {
        MuiTab: {
            disableRipple: true,
        },
    },
    mixins: {
        toolbar: {
            minHeight: 48,
        },
    },
});

theme = {
    ...theme,
    overrides: {
        MuiDrawer: {
            paper: {
                backgroundColor: '#18202c',
            },
        },
        MuiButton: {
            label: {
                textTransform: 'none',
            },
            contained: {
                boxShadow: 'none',
                '&:active': {
                    boxShadow: 'none',
                },
            },
        },
        MuiTabs: {
            root: {
                marginLeft: theme.spacing(1),
            },
            indicator: {
                height: 3,
                borderTopLeftRadius: 3,
                borderTopRightRadius: 3,
                backgroundColor: theme.palette.common.white,
            },
        },
        MuiTab: {
            root: {
                textTransform: 'none',
                margin: '0 16px',
                minWidth: 0,
                padding: 0,
                [theme.breakpoints.up('md')]: {
                    padding: 0,
                    minWidth: 0,
                },
            },
        },
        MuiIconButton: {
            root: {
                padding: theme.spacing(1),
            },
        },
        MuiTooltip: {
            tooltip: {
                borderRadius: 4,
            },
        },
        MuiDivider: {
            root: {
                backgroundColor: '#404854',
            },
        },
        MuiListItemText: {
            primary: {
                fontWeight: theme.typography.fontWeightMedium,
            },
        },
        MuiListItemIcon: {
            root: {
                color: 'inherit',
                marginRight: 0,
                '& svg': {
                    fontSize: 20,
                },
            },
        },
        MuiAvatar: {
            root: {
                width: 32,
                height: 32,
            },
        },
    },
};

const drawerWidth = 256;

const styles = createStyles({
    root: {
        display: 'flex',
        minHeight: '100vh',
    },
    drawer: {
        [theme.breakpoints.up('sm')]: {
            width: drawerWidth,
            flexShrink: 0,
        },
    },
    app: {
        flex: 1,
        display: 'flex',
        flexDirection: 'column',
    },
    main: {
        flex: 1,
        padding: theme.spacing(6, 4),
        background: '#eaeff1',
    },
    footer: {
        padding: theme.spacing(2),
        background: '#eaeff1',
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
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
});

export interface DashBoardProps extends WithStyles<typeof styles> { }

function DashBoard(props: DashBoardProps) {
    const { classes } = props;
    const [mobileOpen, setMobileOpen] = React.useState(false);



    const { user, signOut } = useAuth();

    function handleSignOut() {
        signOut();
    }

    return (
        <ThemeProvider theme={theme}>
            <div className={classes.root}>
                <CssBaseline />
                <nav className={classes.drawer}>
                    {/* <Hidden smUp implementation="js">
                        <Navigator
                            PaperProps={{ style: { width: drawerWidth } }}
                            variant="temporary"
                            open={mobileOpen}
                            onClose={handleDrawerToggle}
                            perfil={user?.perfil}
                        />
                    </Hidden> */}
                    {/* <Hidden xsDown implementation="css"> */}
                    <Navigator PaperProps={{ style: { width: drawerWidth } }} perfil={user?.perfil} />
                    {/* </Hidden> */}
                </nav>
                <div className={classes.app}>
                    <Header usuario={user?.name} />
                    <main className={classes.main}>
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


                    </main>
                    <footer className={classes.footer}>
                        rodapé
                    </footer>
                </div>
            </div>
        </ThemeProvider>
    );
}

export default withStyles(styles)(DashBoard);