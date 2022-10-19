import React, { useState } from "react";
import { useLocation, Link, useHistory } from "react-router-dom";

function AddCollaborators() {

    const [appUsers, setAppUsers] = useState([]);
    const location = useLocation();
    const from = location.state;
    const history = useHistory();


    const searchUsers = async (evt) => {
        evt.preventDefaul();
        const init = {
            headers: {
                Authorization: `Bearer ${auth.user.token}`,
                "Content-Type": "application/json",
            },
            params: searchKey
        };

        const { data } = await fetch("http://localhost:8080/api/playlists/users", init);

        setAppUsers(data.appUsers.items);
    }


    function addUserAsCollaborator(i) {

        const toSave = appUsers[i];


        const init = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                Authorization: `Bearer ${auth.user.token}`
            },
            body: JSON.stringify({
                appUserId: toSave.appUserId,
                playlistId: from,
                accepted: false
            })
        };

        fetch(`http://localhost:8080/api/playlist/${from}/track`, init)
        .then(response => {
            if ( response.status === 201 ) {
                history.push("/playlist/" + from)
            }
        })
    }


    const renderUsers = () => {
        return appUsers.map( (user, i) => (
            <div key={user.appUserId}>
                {user.firstName}
                {user.lastName}
                {user.email}
                <button className="btn btn-success ms-1 me-2" onClick={() => addUserAsCollaborator(i)}>+</button>
            </div>

        ));
    };
    console.log(auth.spotifyToken);

    return (
        <div>
            <h1>Add Collaborators to {playlist.playlistData.playlistId}</h1>

            <form onSubmit={searchTracks}>
                <input type="text" onChange={e => setSearchKey(e.target.value)} />
                <button type={"submit"}>Search</button>
            </form>
            {renderUsers()};
            <div>
                <Link className="btn btn-warning" to={`/playlist/${playlist.playlistData.playlistId}`}>Return to Playlist</Link>
            </div>
        </div>
    );
}

export default AddCollaborators;