import { useState, useEffect, useContext, Link } from "react";
import React from "react";
import AuthContext from "../context/AuthContext";
import UserPlaylists from "./UserPlaylists";
import CollabPlaylists from "./CollabPlaylists";
import PlaylistInvites from "./PlaylistInvites";
// import { Link } from 'react-router-dom';
import { useHistory } from "react-router-dom";

function Home() {

  const auth = useContext(AuthContext);

  const [userPlaylists, setUserPlaylists] = useState([]);

  const [collabPlaylists, setCollabPlaylists] = useState([]);

  const [playlistInvites, setPlaylistInvites] = useState([]);

  const history = useHistory();

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

  function linkToAdd(evt) {
    evt.preventDefault();
    history.push("/addplaylist")
  }


  return (
    <div >
      <div className="container text-center mt-2">

        <div className="card specialCard">
          <h1 style={{ opacity: 1 }}>Collaborative Playlist Creation Station</h1>
        </div>

        {auth.user ? <button className="btn btn-success mt-3" onClick={linkToAdd}>Add Playlist</button>
          : null}
        {auth.user ?
          <div className="container">
            <div className="container mt-4 text center">
              <div className="text-center">
                <div className="card specialCard m-1" style={{ width: "20rem", display: "inline-block" }}>
                  <h3>Your Playlists</h3>
                </div>
              </div>
              {userPlaylists.length > 0 ?
                userPlaylists.map(u => <UserPlaylists key={u.playlistId} playlistData={u} />)
                : "Added or saved playlists will appear here"}
            </div>
            <div className="container mt-3">
              <div className="text-center">
                <div className="card specialCard m-1" style={{ width: "25rem", display: "inline-block" }}>
                  <h3>Collaborating Playlists</h3>
                </div>
              </div>
              {collabPlaylists.length > 0 ?
                collabPlaylists.map(c => <CollabPlaylists key={c.playlistId} playlistData={c} />)
                : "Playlists you are collaborating on will appear here"}
            </div>
            <div className="container mt-5">
              <div className="text-center">
              <div className="card specialCard m-1" style={{ width: "20rem", display: "inline-block" }}>
                <h4>Playlist Invites</h4>
              </div>
              </div>
              {playlistInvites.length > 0 ?
                playlistInvites.map(i => <PlaylistInvites key={i.playlistId} playlistData={i} />)
                : "No collaboration invites at the moment"}
            </div>
          </div>
          : <div className="card m-2 p-2 specialCard" style={{width: "30rem", display: "inline-block"}}>
              <p>Register and Login to view your playlists!</p>
            </div>}
      </div>
    </div>
  );
}

export default Home;
