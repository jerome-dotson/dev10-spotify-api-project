import React from "react";
import { useState, useEffect } from "react";
import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom";
import jwtDecode from "jwt-decode";
import Confirmation from "./components/Confirmation";
import Error from "./components/Error";
import Home from "./components/Home";
import NavBar from "./components/NavBar";
import NotFound from "./components/NotFound";
import Login from "./components/Login";
import Register from "./components/Register";
import AuthContext from "./context/AuthContext";
import PlaylistSearch from "./components/PlaylistSearch";
import UserPage from "./components/UserPage";
import SongSearch from "./components/SongSearch";



const LOCAL_STORAGE_TOKEN_KEY = "ourAppToken";
const LOCAL_STORAGE_SPOTIFY_KEY = "spotifyToken";


function App() {

    // const history = useHistory();

    const [user, setUser] = useState(null);
    const [spotToken, setSpotToken] = useState(null);
    const [restoreLoginAttemptCompleted, setRestoreLoginAttemptCompleted] = useState(false);


    useEffect(() => {
        const token = localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY);
        let spotifyToken = localStorage.getItem(LOCAL_STORAGE_SPOTIFY_KEY);
        if (token) {
            login(token);
        }else{
            spotifyToken = null;
            localStorage.setItem(LOCAL_STORAGE_SPOTIFY_KEY, null);
        }
        
        if (spotifyToken){
            setSpotToken(spotifyToken);
        }

        setRestoreLoginAttemptCompleted(true);

    }, []);


    const login = (token) => {
        localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY, token);

        const { sub: username, authorities: authoritiesString } = jwtDecode(token);

        const roles = authoritiesString.split(',');

        const user = {
            username,
            roles,
            token,
            hasRole(role) {
                return this.roles.includes(role);
            }
        };

        console.log(user);

        setUser(user);

        return user;
    };


    const logout = () => {
        setUser(null);
        setSpotToken(null);
        localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY);
        localStorage.removeItem(LOCAL_STORAGE_SPOTIFY_KEY);
        // history.push("/");
    };


    const auth = {
        user: user ? { ...user } : null,
        spotifyToken : spotToken,

        login,
        logout
    };


    if (!restoreLoginAttemptCompleted) {
        return null;
    }


    return (
        <AuthContext.Provider value={auth}>
            <Router>
                <NavBar />

                <Switch>

                    <Route path="/confirmation">
                        <Confirmation />
                    </Route>

                    <Route path="/error">
                        <Error />
                    </Route>

                    <Route path="/login">
                        {!user ? <Login /> : <Redirect to="/" />}
                    </Route>

                    <Route path="/playlistsearch">
                        <PlaylistSearch />
                    </Route>

                    <Route path="/register">
                        {!user ? <Register /> : <Redirect to="/" />}
                    </Route>

                    <Route path="/userpage">
                        <UserPage onSpotifyTokenUpdated ={setSpotToken} onLogout={logout} />
                    </Route>

                    <Route path="/songsearch">
                        <SongSearch />
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