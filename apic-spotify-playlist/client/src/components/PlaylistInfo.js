import React from "react";
import { useState, useContext, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
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

    function msToMinSec(millis) {
        var minutes = Math.floor(millis / 60000);
        var seconds = ((millis % 60000) / 1000).toFixed(0);
        return (
            seconds == 60 ?
                (minutes + 1) + ":00" :
                minutes + ":" + (seconds < 10 ? "0" : "") + seconds
        );
    }

    //need to add buttons that allow person who added or group admin to remove from list
    const renderTracks = () => {
        return playlist.tracks.map(track => (
            <div key={track.id} className="card" style={{ display: 'inline-block', width: '100%' }}>
                {track.name} &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                {track.artist} &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                {msToMinSec(track.duration)} &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                {auth.user.userId == playlist.appUserId ?
                    <button className="btn btn-success btn-sm ms-1 me-2" onClick={removeTrackFromPlaylist}>-</button>
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
            {playlist ?
                <div>
                    <div style={{ display: 'inline-block' }}>
                        <h1 className="">{playlist.name}</h1> 
                        <h4 className=""> Hosted by: &nbsp; {playlist.appUser.username}</h4>
                        <p>{playlist.collaborators.length} Collaborators</p>
                        <Link to={{pathname: "/songsearch", state: { from: `${playlist.playlistId}` },}} className="btn btn-info m-2">Add Songs</Link>
                    </div>
                    <div className="card" style={{ display: 'inline-block', width: '80%' }}>
                        <div className="card-header">
                            <h5><strong>Song Name &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                                Artist &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                                Length</strong></h5>
                        </div>
                        {renderTracks()}
                    </div>
                    <p>{playlist.tags}</p>

                    <div>
                        {auth.user.userId != playlist.appUserId ?
                            <button className="btn btn-success m-2">Add to Favorites</button>
                            : null}
                    </div>

                    <div>
                        {auth.user.userId == playlist.appUserId ?
                            <Link className="btn btn-danger m-2" to={`/playlist/delete/${playlist.playlistId}`}>Delete Playlist</Link>
                            : null}
                    </div>

                </div>
                : "Error loading"}
            <div>
                {auth.user ?
                    <button className="btn btn-primary m-2">Open Playlist in Spotify</button>
                    : null}
            </div>
        </div>
    );
}

export default PlaylistInfo;