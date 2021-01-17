import React from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";

import SignIn from '../pages/SignIn';


const AuthRoutes: React.FC = () => (
  <BrowserRouter>
    <Switch>
        <Route exact path="/" component={SignIn} />
        <Route path="/signup" component={() => <h1>SignUp</h1>} />
      
        <Route path="*" component={() => <h1>Page not found</h1>} />
    </Switch>
  </BrowserRouter>
);

export default AuthRoutes;

