import React, { FormEvent, useContext, useState } from "react";
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';

import { useAuth } from "../../contexts/auth";
import api from "../../services/api";
import Toast from "../../Components/Toast";


const useStyles = makeStyles((theme) => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    toast: {
        margin: theme.spacing(1),
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.primary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
}));


const FormImplantacao: React.FC = () => {

    const [mensagem, setMensagem] = useState(
        {
            open: false,
            texto: '',
            severity: 'sucess'
        }
    );

    const [cnpj, setCnpj] = useState("");
    const [files, setFiles] = useState<any>([]);

    function handleChange(event: any) {
        setFiles(event.target.files);
    };



    const handleImplantarEmpresa = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const formData = new FormData();
        formData.set('cnpj', cnpj)
        for (var x = 0; x < files.length; x++) {
            formData.append('xml', files[x]);
        }

        

       
        // 'multipart/form-data'
       
       
       
        console.log(formData.get('cnpj'));
        api.post("/cupomfiscalxml/implantacao", formData,  {
            headers: {
                'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBUEkgZG8gRsOzcnVtIGRhIEFsdXJhIiwic3ViIjoiMjciLCJpYXQiOjE2MTQ4MTg0NjcsImV4cCI6MTYxNDkwNDg2N30.KlNWajOwwSNlqnjVZS55ql-e78eSZhfAsrmcjgF5wSk',
                'Content-Type': 'application/json',
            },
        })
        .then(response => {
            setMensagem({
                open: true,
                texto: 'Empresa implantada',
                severity: 'success'
            })
            setCnpj('');
            setFiles(['']);
        })
        .catch((error) => {
            
            setMensagem({
                open: true,
                texto: error.response.data.erroMensagem,
                severity: 'error'
            })
        })


    }

    const classes = useStyles();

    return (

        <Container component="main" maxWidth="xs">
            <div className={classes.paper}>
                <div className={classes.toast}>
                    <Toast
                        open={mensagem.open}
                        handleClose={() =>
                            setMensagem({
                                open: false,
                                texto: '',
                                severity: 'sucess'
                            })

                        }
                        severity={mensagem.severity}
                    >
                        {mensagem.texto}
                    </Toast>
                </div>
                <Avatar className={classes.avatar}>
                    <LockOutlinedIcon />
                </Avatar>
                <Typography component="h1" variant="h5">
                    Implantação de uma nova empresa
                </Typography>
                <form className={classes.form}
                    onSubmit={handleImplantarEmpresa}
                >
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        id="cnpj"
                        label="CNPJ"
                        name="cnpj"
                        placeholder="CNPJ"
                        value={cnpj}
                        onChange={(e) => { setCnpj(e.target.value) }}
                        autoFocus
                    />
                    <button className="file-chooser-button" type="button">
                        {(files)
                            ? files.length + ' selecionados'
                            : 'Secione os arquivos'
                        }
                        <input
                            className="file-input"
                            type="file" name="file"
                            //accept={acceptedTypes.toString()}
                            multiple
                            onChange={handleChange}
                        />
                    </button>
                    <Button
                        fullWidth
                        type="submit"
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                        title="Cadastrar"
                    /* onClick={handleSign} */
                    >
                        Implantar Empresa
                    </Button>



                </form>
            </div>

        </Container>
        // fim
    )
}

export default FormImplantacao;