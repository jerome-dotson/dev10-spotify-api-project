import React from "react";
import { Link } from "react-router-dom";
import './PlaylistCards.css';

function DeleteConfirmButton(playlist) {

    return (
        <div className="card text-center playlistCard">
            <div className="card-body">
                <Link className="btn btn-success m-1" to={`/delete/${playlist.playlistId}`}>Delete Playlist</Link>
            </div>
        </div>
    );
}

export default DeleteConfirmButton;