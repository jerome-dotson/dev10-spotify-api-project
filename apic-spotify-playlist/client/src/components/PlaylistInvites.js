import React from "react";
// import {Link} from "react-router-dom";

function PlaylistInvites(playlistInvites) {

    return (
        <div className="card text-center">
            <div className="card-header">
                <h5>{playlistInvites.playlistData.name}</h5>
            </div>
            <div className="card-body inline">
                {/* <Link className="btn btn-success m-1" to={``}>Accept</Link>
                <Link className="btn btn-danger m-1" to={``}>X</Link> */}
                <button className="btn btn-success m-1"></button>
                <button className="btn btn-danger m-1"></button>
            </div>
        </div>
    );
}

export default PlaylistInvites;