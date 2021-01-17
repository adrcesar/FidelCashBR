import React from 'react';
//import { BrowserRouter, Route, Switch, Redirect } from "react-router-dom";

import { AuthProvider } from "./contexts/auth";

import Routes from "./routes";


const App: React.FC = () => {
  return (
    <AuthProvider>
      <Routes />
    </AuthProvider>
  );
}

export default App;