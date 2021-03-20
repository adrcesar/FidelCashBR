import React, { FormEvent, useState } from "react";
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';

import TextField from '@material-ui/core/TextField';

import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';


import api from "../../services/api";
import Toast from "../../Components/Toast";


const useStyles = makeStyles((theme) => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
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
    const [email, setEmail] = useState("");
    const [files, setFiles] = useState<any>([]);

    function handleChange(event: any) {
        setFiles(event.target.files);
    };



    const handleImplantarEmpresa = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const formData = new FormData();
        formData.set('cnpj', cnpj)
        formData.set('email', email)
        for (var x = 0; x < files.length; x++) {
            formData.append('xml', files[x]);
        }

        console.log(formData.get('cnpj'));
        var token = localStorage.getItem('@FIDELCASH/TOKEN');
        var tamanhoToken = token?.length;
        token = 'Bearer ' + token!.substring(1, (tamanhoToken! - 1)) //'tamanhoToken'; // náo quero o primeiro nem o último caracter
        api.post("/cupomfiscalxml/implantacao", formData, {
            headers: {
                'Authorization': token,
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
                setEmail('');
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

            <div className={classes.paper}>

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

                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        type="email"
                        id="email"
                        label="e-mail do responsável da empresa"
                        name="email"
                        placeholder="e-mail"
                        value={email}
                        onChange={(e) => { setEmail(e.target.value) }}
                    />

                    <Button
                        variant="contained"
                        component="label"
                        fullWidth
                        className={classes.submit}
                        color="primary"
                    >
                        {(files.length > 0)
                            ? files.length + ' arquivos selecionado(s)'
                            : 'Selecione os arquivos xml'
                        }
                        <input
                            type="file"
                            hidden
                            multiple
                            onChange={handleChange}
                        />
                    </Button>
                    <Button
                        fullWidth
                        type="submit"
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                        title="Cadastrar"
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