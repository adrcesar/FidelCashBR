import React from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";


import Dashboard from '../pages/Dashboard';
import Importacao from "../pages/Importacao/";
import Pdv from "../pages/Pdv";

const AppRoutes = () => (
    <BrowserRouter>
      <Switch>
        <Route exact path="/" component={Dashboard} />
        <Route exact path="/importacao" component={Importacao} />
        <Route exact path="/pdv" component={Pdv} />
      </Switch>
    </BrowserRouter>
  );

export default AppRoutes;