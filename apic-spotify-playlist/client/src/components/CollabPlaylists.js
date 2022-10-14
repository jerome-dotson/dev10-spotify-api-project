import React from "react";
import {Link} from "react-router-dom";

function CollabPlaylists(collabPlaylists) {

    return (
        <div className="card text-center">
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