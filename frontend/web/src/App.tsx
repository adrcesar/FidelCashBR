import React from 'react';
//import { BrowserRouter, Route, Switch, Redirect } from "react-router-dom";

import { AuthProvider } from "./contexts/auth";
import { ThemeProvider, createMuiTheme } from '@material-ui/core';

import Routes from "./routes";





const App: React.FC = () => {

  const theme = createMuiTheme({
    spacing: 4,
    /* palette: {
      primary: {
        main: '#f44336',
      },
      secondary: {
        main: '#3EA6FF',
      },
      background: {
        default: 'red',
        paper:  '#FFF',
      },
    }, */
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