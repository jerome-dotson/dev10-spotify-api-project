// import { useState, useEffect, useContext } from "react";
import React from "react";
// import AuthContext from "../context/AuthContext";
// import { useHistory } from "react-router-dom";

function Home() {

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

  return(
    <h1>Welcome to Collaborative Spotify Playlist Creation</h1>
  );
}

export default Home;
