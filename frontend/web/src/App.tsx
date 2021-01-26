import React from 'react';
//import { BrowserRouter, Route, Switch, Redirect } from "react-router-dom";

import { AuthProvider } from "./contexts/auth";
import { ThemeProvider, createMuiTheme } from '@material-ui/core';

import Routes from "./routes";
import blue from '@material-ui/core/colors/blue';




const App: React.FC = () => {

  const theme = createMuiTheme({
    palette: {
      primary: {
        main: '#f44336',
      }
    }
  });

  return (
    <ThemeProvider theme={theme}>
      <AuthProvider>
        <Routes />
      </AuthProvider>
    </ThemeProvider>

  );
}

export default App;