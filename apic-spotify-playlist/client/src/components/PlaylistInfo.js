import React from "react";
import { useState, useContext, useEffect } from "react";
import { useParams } from "react-router-dom";
// import Songs from "./Songs";
import AuthContext from "../context/AuthContext";
import MessageDisplay from "./MessageDisplay";



//do we need to use props or pull from id params?
function PlaylistInfo() {

    const { id } = useParams();

    const [playlist, setPlaylist] = useState(null);

    const [error, setError] = useState([]);

    const auth = useContext(AuthContext);

    // const history = useHistory();

    useEffect(() => {
        fetch("http://localhost:8080/api/playlist/" + id, {
            headers: {
                Authorization: `Bearer ${auth.user.token}`
            }
        })
            .then(
                response => {
                    if (response.status === 201) {
                        return response.json();
                    } else {
                        return response.json();
                    }
                }
            )
            .then(data => {
                if (data.playlistId) {
                    setError([]);
                    setPlaylist(data);
                } else {
                    setError(data)
                }
            })
            .catch(err => console.log(err));
    },
        []);

    function removeTrackFromPlaylist() {

    }

    //need to add buttons that allow person who added or group admin to remove from list
    const renderTracks = () => {
        return playlist.tracks.map(track => (
            <div key={track.id}>
                {track.name}
                {track.artistName}
                {track.duration_ms}
                {auth.user.userId == playlist.appUserId ?
                    <button className="btn btn-success ms-1 me-2" onClick={removeTrackFromPlaylist}>-</button>
                    : null}
            </div>
        ));
    };



    //display image, playlist name, creator username, number of accepted collaborators
    //list of tracks on playlist (song name, artist name, length)
    //tags
    //conditional rendering:
    //<Add to favorites(clone) button> and <go to playlist on spotify button>
    return (
        <div className="container text-center">
            <h2 className="m-4">Playlist Info</h2>
            <div>
                {error.length > 0 ? <MessageDisplay error={error} /> : null}
            </div>
            {/* <img src={playlist.image} alt="Playlist image" /> */}
            {playlist ?
                <div>
                    <h3>{playlist.name}</h3>
                    <h3>{playlist.username}</h3>
                    <h3>{playlist.collaborators.length} Collaborators</h3>
                    {renderTracks()};
                    <p>{playlist.tags}</p>
                </div>
                : "Error loading"}
            <div>
                {auth.user ?
                    <button className="btn btn-success m-2">Add to Favorites</button>
                    : null}
            </div>
            <div>
                {auth.user ?
                    <button className="btn btn-primary m-2">Open Playlist in Spotify</button>
                    : null}
            </div>
        </div>
    );
}

export default PlaylistInfo;