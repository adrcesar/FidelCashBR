import React from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";

import Dashboard from '../pages/Dashboard';
import Importacao from "../pages/Importacao/";

const AppRoutes = () => (
    <BrowserRouter>
      <Switch>
        <Route exact path="/" component={Dashboard} />
        <Route exact path="/importacao" component={Importacao} />
      </Switch>
    </BrowserRouter>
  );

export default AppRoutes;