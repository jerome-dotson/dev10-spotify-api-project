// import { useState, useEffect, useContext } from "react";
import React, { useEffect, useState } from "react";
import SpotifyWebApi from "spotify-web-api-js";
import { getTokenFromUrl } from "./getTokenFromUrl";
// import AuthContext from "../context/AuthContext";
// import { useHistory } from "react-router-dom";
import SpotifyAuthButton from "./SpotifyAuthButton";


function Home() {

  const spotify = new SpotifyWebApi();
  const [spotifyToken, setSpotifyToken] = useState("");

  useEffect(() =>{
    console.log("This is what we derived from the URL: ", getTokenFromUrl());
    const _spotifyToken = getTokenFromUrl().access_token;
    window.location.hash = "";

    console.log("This is our Spotify Token.", spotifyToken);

    if(_spotifyToken){
      setSpotifyToken(_spotifyToken);
      spotify.setAccessToken(_spotifyToken);

      spotify.getMe().then((user) => {
        console.log("This is you: ", user);
      });
    }
  }
  );

  // const [userPlaylists, setUserPlaylists] = useState([]);

  // const [savedPlaylists, setSavedPlaylists] = useState([]);

  // const auth = useContext(AuthContext);

  // const history = useHistory();

  //we want to display:
  // - playlists the user has accepted collaboration requests on
  // - playlists the user has been invited to collaborate on but not yet accepted (onChange should re render the accepted playlists list)
  // - playlists the user has saved/cloned to their in app account

  // useEffect(() => {
  //   fetch("http://localhost:8080/playlist/home/" + {auth.user.id} )
  // })

  return(<>
    <h1>Welcome to Collaborative Spotify Playlist Creation</h1>
    <SpotifyAuthButton />
    </>

  );
}

export default Home;
