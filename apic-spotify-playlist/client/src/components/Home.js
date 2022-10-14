import { useState, useEffect, useContext } from "react";
import React from "react";
import AuthContext from "../context/AuthContext";
import UserPlaylists from "./UserPlaylists";
import CollabPlaylists from "./CollabPlaylists";
import PlaylistInvites from "./PlaylistInvites";
// import { useHistory } from "react-router-dom";

function Home() {

  const auth = useContext(AuthContext);

  const [userPlaylists, setUserPlaylists] = useState([]);

  const [collabPlaylists, setCollabPlaylists] = useState([]);

  const [playlistInvites, setPlaylistInvites] = useState([]);

  // const history = useHistory();

  function loadUserPlaylists() {
    fetch("http://localhost:8080/playlist/hosting/" + auth.user.app_user_id)
      .then(
        response => {
          if (response.status === 200) {
            return response.json();
          } else {
            return response.json();
          }
        }
      )
      .then(data => setUserPlaylists(data))
      .catch(err => console.log(err));
  }

  function loadCollabPlaylists() {
    fetch("http://localhost8080/playlist/collaborating/" + auth.user.app_user_id)
      .then(
        response => {
          if (response.status === 200) {
            return response.json();
          } else {
            return response.json();
          }
        }
      )
      .then(data => setCollabPlaylists(data))
      .catch(err => console.log(err));
  }

  function loadPlaylistInvites() {
    fetch("http://localhost8080/playlist/invited/" + auth.user.app_user_id)
      .then(
        response => {
          if (response.status === 200) {
            return response.json();
          } else {
            return response.json();
          }
        }
      )
      .then(data => setPlaylistInvites(data))
      .catch(err => console.log(err));
  }

  useEffect(() => {
    loadUserPlaylists();
    loadCollabPlaylists();
    loadPlaylistInvites();
  }, []);



  return (
    <div className="container text-center">
      <h1>Collaborative Spotify Playlist Creation</h1>
      {auth ?
        <div>
          <div>
            {userPlaylists.length > 0 ?
              userPlaylists.map(u => <UserPlaylists key={u.playlistId} playlistData={u} />)
              : null}
          </div>
          <div>
            {collabPlaylists.length > 0 ?
              collabPlaylists.map(c => <CollabPlaylists key={c.playlistId} playlistData={c} />)
              : null}
          </div>
          <div>
            {playlistInvites.length > 0 ?
              playlistInvites.map(i => <PlaylistInvites key={i.playlistId} playlistData={i} />)
              : null}
          </div>
        </div>
        : null}
    </div>
  );
}

export default Home;
