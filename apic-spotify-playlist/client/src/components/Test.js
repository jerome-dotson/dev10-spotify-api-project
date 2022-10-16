import { useEffect, useState } from 'react';
import { accessToken } from '../spotify';

function Test(){

    const [spotifyToken, setSpotifyToken] = useState(null);

    useEffect(() => {
    
        setSpotifyToken(accessToken); //Go back and edit conditionally rendering link account to Spotify


    }, []);

    return (<a className="App-link" href="http://localhost:8888/login">Log in to Spotify</a>);
}