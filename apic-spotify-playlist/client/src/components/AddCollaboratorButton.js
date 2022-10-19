import React from "react";
import { Link } from "react-router-dom";
import './PlaylistCards.css';

function AddCollaboratorsButton(playlist) {

    return (
        <div className="text-center playlistCard">
            <div className="card-body">
                <Link className="btn btn-info m-1" to={`/addcollab/${playlist.playlistId}`}>Add Collaborators</Link>
            </div>
        </div>
    );
}

export default AddCollaboratorsButton;