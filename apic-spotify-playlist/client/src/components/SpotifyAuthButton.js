import {loginUrl} from './spotify';
import React from 'react';

function SpotifyAuthButton(){

    return (<a href={loginUrl} id="signInButton">Sign in with spotify!</a>);
}

export default SpotifyAuthButton;