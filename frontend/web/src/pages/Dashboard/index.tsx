import React, { useContext } from "react";
import { useAuth } from "../../contexts/auth";


const Dashboard: React.FC = () => {

    const { user, signOut } = useAuth();

    function handleSignOut() {
        signOut();
    }

    return (
        <div className="App">
            <h1>Logado</h1>
            <h3>{user?.name}</h3>
            <button title="Sign Out" onClick={handleSignOut} />
        </div>
    )
}

export default Dashboard;