import { useEffect } from 'react';

function Test(){

    useEffect(() => {
        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        const accessToken = urlParams.get("access_token");
        const refreshToken = urlParams.get("refresh_token");

    }, []);

    return (<a className="App-link" href="http://localhost:8888/login">Log in to Spotify</a>);
}