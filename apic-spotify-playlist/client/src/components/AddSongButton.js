import React from "react";
import { Link } from "react-router-dom";
import './PlaylistCards.css';

function AddSongButton(playlist) {

    return (
        <div className="card text-center playlistCard">
            <div className="card-body">
                <Link className="btn btn-success m-1" to={`/songsearch/${playlist.playlistId}`}>Add Songs</Link>
            </div>
        </div>
    );
}

export default AddSongButton;