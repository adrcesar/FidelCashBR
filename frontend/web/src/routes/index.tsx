import React, { useContext } from "react";

import { useAuth } from "../contexts/auth";

import AuthRoutes from "../routes/auth.routes";
import AppRoutes from "../routes/app.routes";

const Routes: React.FC = () => {
  const { signed } = useAuth();
  return signed ? <AppRoutes /> : <AuthRoutes />;
};

export default Routes;