import React from "react";
import { Link } from "react-router-dom";
import './PlaylistCards.css';

function DeleteTrackConfirmButton(playlist) {

    return (
        <div className="text-center playlistCard">
            <div className="card-body">
                <Link className="btn btn-danger m-1" to={`/delete/${playlist.playlistId}`}>remove</Link>
            </div>
        </div>
    );
}

export default DeleteTrackConfirmButton;