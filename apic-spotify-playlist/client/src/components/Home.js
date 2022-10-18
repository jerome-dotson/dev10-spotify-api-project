import { useState, useEffect, useContext, Link } from "react";
import React from "react";
import AuthContext from "../context/AuthContext";
import UserPlaylists from "./UserPlaylists";
import CollabPlaylists from "./CollabPlaylists";
import PlaylistInvites from "./PlaylistInvites";
// import { Link } from 'react-router-dom';
// import { useHistory } from "react-router-dom";

function Home() {

  const auth = useContext(AuthContext);

  const [userPlaylists, setUserPlaylists] = useState([]);

  const [collabPlaylists, setCollabPlaylists] = useState([]);

  const [playlistInvites, setPlaylistInvites] = useState([]);

  // const history = useHistory();

  function loadUserPlaylists() {
    fetch("http://localhost:8080/api/playlist/hosting/" + auth.user.userId, {
      headers: {
        Authorization: `Bearer ${auth.user.token}`
      },
    })
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
    fetch("http://localhost:8080/api/playlist/collaborating/" + auth.user.userId, {
      headers: {
        Authorization: `Bearer ${auth.user.token}`
      },
    })
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
    fetch("http://localhost:8080/api/playlist/invited/" + auth.user.userId, {
      headers: {
        Authorization: `Bearer ${auth.user.token}`
      },
    })
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
    if (auth.user) {
      loadUserPlaylists();
      loadCollabPlaylists();
      loadPlaylistInvites();
    }
  }, []);



  return (
    <div className="container text-center">
      <h1>Collaborative Spotify Playlist Creation</h1>
      {/* <div className="container">
        <Link className="btn btn-success" to="/addplaylist">Add Playlist</Link>
      </div> */}
      <button ></button>
      {auth.user ?
        <div className="container">
          <div className="container mt-5">
            <h3>Your Playlists</h3>
            {userPlaylists.length > 0 ? 
              userPlaylists.map(u => <UserPlaylists key={u.playlistId} playlistData={u} />)
              : "Added or saved playlists will appear here"}
          </div>
          <div className="container mt-3">
          <h3>Collaborating Playlists</h3>
            {collabPlaylists.length > 0 ?
              collabPlaylists.map(c => <CollabPlaylists key={c.playlistId} playlistData={c} />)
              : "Playlists you are collaborating on will appear here"}
          </div>
          <div className="container mt-5">
          <h4>Playlist Invites</h4>
            {playlistInvites.length > 0 ?
              playlistInvites.map(i => <PlaylistInvites key={i.playlistId} playlistData={i} />)
              : "No collaboration invites at the moment"}
          </div>
        </div>
        : "Login to view your playlists!"}
    </div>
  );
}

export default Home;
