import React, { FormEvent, useState } from "react";

import Avatar from "@material-ui/core/Avatar/Avatar";
import Container from "@material-ui/core/Container/Container";
import { makeStyles } from "@material-ui/core/styles";
import api from "../../services/api";
import Toast from "../Toast";
import ComputerIcon from '@material-ui/icons/Computer';
import Typography from "@material-ui/core/Typography/Typography";
import TextField from "@material-ui/core/TextField/TextField";
import Button from "@material-ui/core/Button/Button";

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


const FormPdv: React.FC = () => {
    const [mensagem, setMensagem] = useState(
        {
            open: false,
            texto: '',
            severity: 'sucess'
        }
    );

    const [macAddress, setMacAddress] = useState("");
    const [cnpj, setCnpj] = useState("");
    const [pastaDeUpload, setPastaDeUpload] = useState("");

    const handleCadastrarPdv = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        var token = localStorage.getItem('@FIDELCASH/TOKEN');
        var tamanhoToken = token?.length;
        token = 'Bearer ' + token!.substring(1, (tamanhoToken! - 1)) //'tamanhoToken'; // náo quero o primeiro nem o último caracter
        api.post("/pdv", {macAddress, cnpj, pastaDeUpload}, {
            headers: {
                'Authorization': token,
                'Content-Type': 'application/json',
            },
        })
            .then(response => {
                setMensagem({
                    open: true,
                    texto: 'Pdv cadastrado.',
                    severity: 'success'
                })
                setMacAddress('');
                setCnpj('');
                setPastaDeUpload('');
            })
            .catch((error) => {
                setMensagem({
                    open: true,
                    texto: error.response.data.mensagemErro,
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
                    <ComputerIcon />
                </Avatar>
                <Typography component="h1" variant="h5">
                    Cadastro de PDV da empresa
                </Typography>
                <form className={classes.form}
                    onSubmit={handleCadastrarPdv}
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
                        id="macAddress"
                        label="Mac address"
                        name="macAddress"
                        placeholder="Mac address"
                        value={macAddress}
                        onChange={(e) => { setMacAddress(e.target.value) }}
                    />

                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        id="pastaDeUpload"
                        label="Pasta de upload dos cupons fiscais"
                        name="pastaDeUpload"
                        placeholder="Pasta de upload dos cupons fiscais"
                        value={pastaDeUpload}
                        onChange={(e) => { setPastaDeUpload(e.target.value) }}
                    />

                    <Button
                        fullWidth
                        type="submit"
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                        title="Cadastrar"
                    >
                        Cadastrar PDV
                    </Button>



                </form>
            </div>

        </Container>

    )}

export default FormPdv; 
