import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Login from './Pages/Login/Login';
import {BrowserRouter, Switch, Route} from 'react-router-dom';

ReactDOM.render(
    <BrowserRouter>
        <Switch>
            <Route path='/' exact={true} component={Login}/>
        </Switch>
    </BrowserRouter>,
document.getElementById('root'));

