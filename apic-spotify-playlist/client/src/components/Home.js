// import { useState, useEffect, useContext } from "react";
import React from "react";
// import AuthContext from "../context/AuthContext";
// import { useHistory } from "react-router-dom";

function Home() {

  // const [userPlaylists, setUserPlaylists] = useState([]);

  // const [collabPlaylists, setCollabPlaylists] = useState([]);

  // const [playlistInvites, setPlaylistInvites] = useState([]);

  // const auth = useContext(AuthContext);

  // const history = useHistory();

  //we want to display:
  // - playlists the user is hosting (userPlaylists)
  // - playlists the user has accepted collaboration requests on (collabPlaylists)
  // - playlists the user has been invited to collaborate (playlistInvites) (onChange should re render the accepted playlists list)

  // useEffect(() => {
  //   fetch("http://localhost:8080/playlist/home/" + {auth.user.id} )
  // })

  return(
    <div className="container text-center">
    <h1>Collaborative Spotify Playlist Creation</h1>
    </div>
  );
}

export default Home;
