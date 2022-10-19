import React, { useState, useContext } from "react";
import { useLocation, Link, useHistory, useParams } from "react-router-dom";
import AuthContext from "../context/AuthContext";

function AddCollaborators() {

    const [appUsers, setAppUsers] = useState([]);
    const location = useLocation();
    const from = location.state;
    const history = useHistory();
    const auth = useContext(AuthContext);
    const [searchKey, setSearchKey] = useState("");
    const [playlist, setPlaylist] = useState([]);
    const { id } = useParams();


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
                if (response.status === 201) {
                    history.push("/playlist/" + from)
                }
            })
    }


    const renderUsers = () => {
        return appUsers.map((user, i) => (
            <div key={user.appUserId}>
                {user.firstName}
                {user.lastName}
                {user.email}
                <button className="btn btn-success ms-1 me-2" onClick={() => addUserAsCollaborator(i)}>+</button>
            </div>

        ));
    };

    return (
        <div className="container text-center">
            <div className="card text-center p-2 m-5" style={{ width: '40rem' }}>
                <h1 className="card-header">Add Collaborators</h1>
                <form onSubmit={searchUsers}>
                    <input type="text"
                        className="form-control m-3"
                        style={{ width: '37rem' }}
                        onChange={e => setSearchKey(e.target.value)} />
                    <button type={"submit"} className="btn btn-success m-1">Search</button>
                    <Link className="btn btn-warning" to={"/playlist/" + id}>Return to Playlist</Link>
                </form>
                {appUsers.map((user, i) => 
                    <div key={user.appUserId}>
                        {user.firstName} &nbsp; &nbsp;
                        {user.lastName} &nbsp; &nbsp;
                        {user.email} &nbsp; &nbsp;
                        <button className="btn btn-info btn sm ms-1 me-2" onClick={() => addUserAsCollaborator(i)}>+</button>
                    </div>)}
            </div>
        </div>
    );
}

export default AddCollaborators;