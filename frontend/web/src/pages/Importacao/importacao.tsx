import React, { FormEvent, InputHTMLAttributes, useState } from "react";

import { Container } from "@material-ui/core";
import Toast from "../../Components/Toast";

import CloudUploadIcon from '@material-ui/icons/CloudUpload';
import Avatar from "@material-ui/core/Avatar";
import Typography from "@material-ui/core/Typography";
import makeStyles from "@material-ui/core/styles/makeStyles";
import Button from "@material-ui/core/Button";

import api from "../../services/api";

//GAMBIARRA ACHADA NA INTERNET DEPOIS DE MUITO CUSTO
declare module 'react' {
    interface HTMLAttributes<T> extends AriaAttributes, DOMAttributes<T> {
      // extends React's HTMLAttributes
      directory?: string;
      webkitdirectory?:string;
    }
  }

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

//PEGO TODAS AS PROPRIEDADES DO ELEMENTO INPUT DO HTML


const Importacao: React.FC = () => {
    const [mensagem, setMensagem] = useState(
        {
            open: false,
            texto: '',
            severity: 'sucess'
        }
    );

    const [files, setFiles] = useState<any>([]);

    function handleChange(event: any) {
        setFiles(event.target.files);
    };

    const handleImplantarEmpresa = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const formData = new FormData();
        
        for (var x = 0; x < files.length; x++) {
            formData.append('xml', files[x]);
            console.log(formData.get('xml'));
        }

        
        var token = localStorage.getItem('@FIDELCASH/TOKEN');
        var tamanhoToken = token?.length;
        token = 'Bearer ' + token!.substring(1, (tamanhoToken! - 1)) //'tamanhoToken'; // náo quero o primeiro nem o último caracter

        api.post("/cupomfiscalxml/importacao", formData, {
            headers: {
                'Authorization': token,
                'Content-Type': 'application/json',
            },
        })
        .then(response => {
            setMensagem({
                open: true,
                texto: 'Cupons importados com sucesso.',
                severity: 'success'
            })
            
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
                    <CloudUploadIcon />
                </Avatar>

                <Typography component="h1" variant="h5">
                    Upload de arquivos
                </Typography>

                <form className={classes.form}
                      onSubmit={handleImplantarEmpresa}
                >
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
                            /* directory="" */
                            webkitdirectory=""
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
                        Upload de arquivos
                    </Button>
                </form>

            </div>
        </Container>
    );

    
}



export default Importacao;