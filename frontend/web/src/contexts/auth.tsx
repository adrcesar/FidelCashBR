import React, { createContext, useState, useEffect, useContext } from "react";
import * as auth from "../services/auth";
import api from "../services/api";


interface User {
    name: string;
    email: string;
}

interface AuthContextData {
    signed: boolean;
    user: User | null;
    signIn(token: string, name: string, email: string): Promise<void>; /* não recebe nenhum parâmetro e o retorno é uma promise que não tem retorno (void), */
    signOut(): void;
}

/* Se eu passase apenas um createContext<AuthContextData>({}); o vscode iria me mostrar um erro de typescript:
Argument of type '{}' is not assignable to parameter of type 'AuthContextData'.
O argumento do tipo objeto vazio não é atribuível ao tipo de parâmetro AuthContextData. 
Então, preciso fazer o hack: {} as AuthContextData falando que {} é representado como um AuthContextData.
 */
const AuthContext = createContext<AuthContextData>({} as AuthContextData);

/* Outra maneira de contornar o problema seria assim:
const AuthContext = createContext<AuthContextData | null>(null);
AuthContext recebe um AuthContextData ou null e início a constante com null como parâmetro da função. Mas eu não acho tão elegante. Do outro 
jeito é o mais recomendável fazer e comumente utilizado pela comunidade.*/

const AuthProvider: React.FC = ({ children }) => {
    const [user, setUser] = useState<User | null>(null);  /*pode ser object ou nulo*/



    useEffect(() => {

        async function loadStorageData() {
            const storagedUser = localStorage.getItem('@FIDELCASH/USER');
            const storagedToken = localStorage.getItem('@FIDELCASH/TOKEN');
            console.log("auth " + storagedToken);
            if (storagedUser && storagedToken) {
                setUser(JSON.parse(storagedUser));
                api.defaults.headers.Authorization = `Baerer ${storagedToken}`;
            }

        }



        loadStorageData(); //NAO É POSSÍVEL USAR ASYNC NO PARAMETRO DA USEeFFECT, POR ISSO A CRIACAO DA FUNCTION E A CHAMADA DELA 
    });

    async function signIn(token: string, name: string, email: string ) {
        const response = await auth.signIn(token, name, email);
        setUser(response.user);

        api.defaults.headers.Authorization = `Baerer ${response.token}`;

        localStorage.setItem('@FIDELCASH/USER', JSON.stringify(response.user));
        localStorage.setItem('@FIDELCASH/TOKEN', JSON.stringify(response.token));

        
    }

    function signOut() {
        localStorage.removeItem('@FIDELCASH/USER');
        localStorage.removeItem('@FIDELCASH/TOKEN');
        setUser(null);
    }



    return (

        <AuthContext.Provider value={{ signed: !!user, user, signIn, signOut }}>
            {children}
        </AuthContext.Provider>
    );
};

function useAuth() {
    const context = useContext(AuthContext);
  
    if (!context) {
      throw new Error('useAuth must be used within an AuthProvider.');
    }
  
    return context;
  }

export {AuthProvider, useAuth};