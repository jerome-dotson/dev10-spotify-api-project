import React from "react";
import { Link } from "react-router-dom";
import './PlaylistCards.css';

function AddCollaboratorsButton(playlist) {

    return (
        <div className="card text-center playlistCard">
            <div className="card-body">
                <Link className="btn btn-success m-1" to={`/addcollab/${playlist.playlistId}`}>Add Collaborators</Link>
            </div>
        </div>
    );
}

export default AddCollaboratorsButton;