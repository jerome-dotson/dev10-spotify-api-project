// NEW: Import the useEffect hook
import React from "react"; 
import { useState, useEffect } from "react";
import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom";
import jwtDecode from "jwt-decode";
import Confirmation from "./components/Confirmation";
import Error from "./components/Error";
import Home from "./components/Home";
import NavBar from "./components/NavBar";
import NotFound from "./components/NotFound";
// import SightingForm from "./components/SightingForm";
import Login from "./components/Login";
import AuthContext from "./context/AuthContext";


// NEW: Define a variable for the localStorage token item key
const LOCAL_STORAGE_TOKEN_KEY = "bugSafariToken";

function App() {
    const [user, setUser] = useState(null);
    // NEW: Define a state variable to track if 
    // the restore login attempt has completed
    const [restoreLoginAttemptCompleted, setRestoreLoginAttemptCompleted] = useState(false);

    // NEW: Define a useEffect hook callback function to attempt
    // to restore the user's token from localStorage
    useEffect(() => {
        const token = localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY);
        if (token) {
            login(token);
        }
        setRestoreLoginAttemptCompleted(true);
    }, []);

    const login = (token) => {
        // NEW: set the token in localStorage
        localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY, token);

        // Decode the token
        const { sub: username, authorities: authoritiesString } = jwtDecode(token);

        // Split the authorities string into an array of roles
        const roles = authoritiesString.split(',');

        // Create the "user" object
        const user = {
            username,
            roles,
            token,
            hasRole(role) {
                return this.roles.includes(role);
            }
        };

        // Log the user for debugging purposes
        console.log(user);

        // Update the user state
        setUser(user);

        // Return the user to the caller
        return user;
    };

    const logout = () => {
        setUser(null);
        // NEW: remove the token from localStorage
        localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY);
    };

    const auth = {
        user: user ? { ...user } : null,
        login,
        logout
    };

    // NEW: If we haven't attempted to restore the login yet...
    // then don't render the App component
    if (!restoreLoginAttemptCompleted) {
        return null;
    }

    return (
        <AuthContext.Provider value={auth}>
            <Router>
                <NavBar />

                <Switch>
                    <Route path={["/edit/:id", "/add"]}>
                        {/* {user ? <SightingForm /> : <Redirect to="/login" />} */}
                    </Route>

                    <Route path="/confirmation">
                        <Confirmation />
                    </Route>

                    <Route path="/error">
                        <Error />
                    </Route>

                    <Route path="/login">
                        {!user ? <Login /> : <Redirect to="/" />}
                    </Route>

                    <Route exact path="/">
                        <Home />
                    </Route>

                    <Route>
                        <NotFound />
                    </Route>
                </Switch>
            </Router>
        </AuthContext.Provider>
    );
}

export default App;