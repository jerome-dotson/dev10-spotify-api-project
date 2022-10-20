
import React, { useState, Link, useContext } from "react";
import { useHistory } from "react-router-dom";
import AuthContext from "../context/AuthContext";
import MessageDisplay from "./MessageDisplay";


const DEFAULT_PLAYLIST = {
    playlistId: "",
    name: "",
    description: "",
    appUserId: ""
}

function AddPlaylist() {

    const [newPlaylist, setNewPlaylist] = useState(DEFAULT_PLAYLIST);

    const [error, setError] = useState([]);

    const history = useHistory();

    const auth = useContext(AuthContext)

    const handleChange = (evt) => {
        const propertyName = evt.target.name;
        const newValue = evt.target.value;

        const toAdd = { ...newPlaylist };
        toAdd[propertyName] = newValue;
        setNewPlaylist(toAdd);
    }

    // function assignOwner() {
    //     const propertyName = "appUserId";
    //     const newValue = auth.user.userId;

    //     const toAdd = { ...newPlaylist };
    //     toAdd[propertyName] = newValue;
    //     setNewPlaylist(toAdd);
    // }

    const handleSubmit = async (evt) => {
        evt.preventDefault();
        
        const propertyName = "appUserId";
        const newValue = auth.user.userId;

        let toAdd = { ...newPlaylist };
        toAdd[propertyName] = newValue;
        setNewPlaylist(toAdd);

        const toAddAssembled = { ...newPlaylist };

        fetch("http://localhost:8080/api/playlist", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                Authorization: `Bearer ${auth.user.token}`,
            },
            body: JSON.stringify(toAddAssembled)
        })
            .then(response => {
                if (response.status === 201) {
                    console.log("Playlist Created");
                    history.push("/");
                    return response.json();
                } else {
                    return response.json();
                }
            })
            .then((data) => {
                if (data.playlistId) {
                    setError([]);
                } else {
                    setError(data);
                }
            })
            .catch((err) => {
                console.log(err);
                setError(["Unknown Error"])
            })
    }

    function handleCancel(evt) {
        evt.preventDefault();
        history.push("/");
    }

    //TODO: adjust the tag entry below to search for tags by partial string

    return (
        <div className="text-center">
            <div className="card m-5 specialCard" style={{ width: '18rem', display: "inline-block" }}>
                <h2 className="card-header text-center">Create a Playlist</h2>
                <div>
                    {error.length > 0 ? <MessageDisplay key={error} error={error} /> : null}
                </div>
                <form onSubmit={handleSubmit} className="card-body">

                    <div className="card-text form-group">
                        <label htmlFor="name">Playlist name:</label>
                        <input
                            type="text"
                            className="form-control mb-2"
                            placeholder="Enter Playlist Name"
                            value={newPlaylist.name}
                            onChange={handleChange}
                            id="name"
                            name="name"
                            required
                        />
                    </div>

                    <div className="card-text form-group">
                        <label htmlFor="description">Description:</label>
                        <input
                            type="text"
                            className="form-control mb-2"
                            placeholder="Enter Description"
                            value={newPlaylist.description}
                            onChange={handleChange}
                            id="description"
                            name="description"
                        />
                    </div>

                    {/* <div className="card-text form-group">
                        <label htmlFor="tags">Tags:</label>
                        <input
                            type="text"
                            className="form-control mb-2"
                            placeholder="Enter Tags"
                            onChange={(event) => setPlaylistTag(event.target.value)}
                            id="tags"
                        />
                    </div> */}
                    <div>
                        <button type="submit" className="btn btn-success m-2">Create Playlist</button>
                        <button type="button" className="btn btn-danger m-2" onClick={handleCancel}>Cancel</button>
                        {/* <Link to="/" className="btn btn-warning mt-2 mb-2">Cancel</Link> */}
                    </div>
                </form>
            </div>

        </div>
    );
}

export default AddPlaylist;