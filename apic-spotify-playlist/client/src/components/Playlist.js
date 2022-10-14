import React from "react";
import { Link } from "react-router-dom";

function Playlist(playlist) {
    // display playlist name and username associated with app_user_id attached
    // on click links to playlistinfo page for selected playlist
    return (<Link to={`/playlist/${playlist.playlistData.playlist_id}`}>
        <h5>{playlist.playlistData.name}</h5>
        <p>{playlist.playlistData.username}</p>
    </Link>);
}

export default Playlist;