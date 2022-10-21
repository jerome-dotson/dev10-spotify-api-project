import React, { useContext, useState } from "react";
// import {Link} from "react-router-dom";
import './PlaylistCards.css';
import AuthContext from "../context/AuthContext";
import { useHistory } from "react-router-dom";
import MessageDisplay from "./MessageDisplay";


function PlaylistInvites(playlistInvites) {

const auth = useContext(AuthContext);

    const [error, setError] = useState([]);

    const history = useHistory();

    function acceptInvite(evt) {
        evt.preventDefault();
            //needs to update accepted bool on collab table to 1 where playlistid=? and userid=?
        fetch(`http://localhost:8080/api/playlist/invite/accept/${playlistInvites.playlistData.playlistId}/${auth.user.userId}`, {
            method: "PUT", 
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${auth.user.token}`
            },
            body: JSON.stringify({
                appUserId: `${auth.user.userId}`,
                playlistId: `${playlistInvites.playlistData.playlistId}`,
                accepted: true,
            })
        })
        .then(response => {
            if(response.status === 204) {
                console.log("Invite Accepted")
                window.location.reload(false);
            } else {
                console.log("Failure");
            }
        })
    }

    function deleteInvite(evt) {
        evt.preventDefault();

        fetch(`http://localhost:8080/api/playlist/invite/deny/${playlistInvites.playlistData.playlistId}/${auth.user.userId}`, {
            method: "DELETE",
            headers: {
                Authorization: `Bearer ${auth.user.token}`
            },
        })
        .then( response => {
            if ( response.status === 204 ){
                window.location.reload(false);
            } else {
                setError(response);
            }
        })

    }

    return (
        <div className="card text-center playlistCards specialCard">
            <div className="card-header">
                <h5>{playlistInvites.playlistData.name}</h5>
            </div>
            <div>
                {error.length > 0 ? <MessageDisplay error={error} /> : null}
            </div>
            <div className="card-body inline">

                <button className="btn btn-success m-1" onClick={acceptInvite}>Accept</button>
                <button className="btn btn-danger m-1" onClick={deleteInvite}>Deny</button>
            </div>
        </div>
    );
}

export default PlaylistInvites;