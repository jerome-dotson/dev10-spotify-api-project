import React from "react";

function AddCollaborators() {




    const renderUsers = () => {
        return appUsers.map(user => (
            <div key={user.appUserId}>
                {user.firstName}
                {user.lastName}
                {user.email}
                <button className="btn btn-success ms-1 me-2" onClick={addUserAsCollaborator}>+</button>
            </div>

        ));
    };
    console.log(auth.spotifyToken);

    return (
        <div>
            <h1>Add Collaborators to {}</h1>

            <form onSubmit={searchTracks}>
                <input type="text" onChange={e => setSearchKey(e.target.value)} />
                <button type={"submit"}>Search</button>
            </form>
            {renderUsers()};
            <div>
                <Link className="btn btn-warning" to={`/playlist/${playlist.playlistData.playlist_id}`}>Return to Playlist</Link>
            </div>
        </div>
    );
}

export default AddCollaborators;