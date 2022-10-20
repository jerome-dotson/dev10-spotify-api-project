import React from "react";
import {Link} from "react-router-dom";
import './PlaylistCards.css';

function CollabPlaylists(collabPlaylists) {

    return (
        <div className="card text-center playlistCard specialCard">
            <div className="card-header">
                <h5>{collabPlaylists.playlistData.name}</h5>
            </div>
            <div className="card-body">
                <Link className="btn btn-success m-1" to={`playlist/${collabPlaylists.playlistData.playlistId}`}>View</Link>
            </div>
        </div>
    );
}

export default CollabPlaylists;