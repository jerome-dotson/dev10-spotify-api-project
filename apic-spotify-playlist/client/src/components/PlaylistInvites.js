import React from "react";
// import {Link} from "react-router-dom";
import './PlaylistCards.css';


function PlaylistInvites(playlistInvites) {

    return (
        <div className="card text-center playlistCards">
            <div className="card-header">
                <h5>{playlistInvites.playlistData.name}</h5>
            </div>
            <div className="card-body inline">
                {/* <Link className="btn btn-success m-1" to={``}>Accept</Link>
                <Link className="btn btn-danger m-1" to={``}>X</Link> */}
                <button className="btn btn-success m-1">Accept</button>
                <button className="btn btn-danger m-1">Deny</button>
            </div>
        </div>
    );
}

export default PlaylistInvites;