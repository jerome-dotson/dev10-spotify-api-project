import React from "react";

function AddPlaylist() {

    const [playlistName, setPlaylistName] = useState("");
    const [playlistTag, setPlaylistTag] = useState("");
    const [playlistDescription, setPlaylistDescription] = useState("");
    const [creatorId, setCreatorId] = useState("");
    const [error, setError] = useState([]);

    const history = useHistory();

    const handleSubmit = async (evt) => {
        evt.preventDefault();
        setError([]);

        setCreatorId(auth.user.userId)

        fetch("heep://localhost:8080/api/playlist/add", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${auth.user.token}`,
            },
            body: JSON.stringify({
                playlistName,
                playlistDescription,
                creatorId
            })
        })
        .then(response => {
            if ( response.status === 201 ) {
                console.log("Playlist Created")



                history.push("/")
            } else {
                return response.json();
            }
        })
        .then((data) => {
            setError(data);
        })
        .catch((err) => {
            console.log(err);
            setError(["Unknown Error"])
        })
    }

    function getPlaylistId

    return(
        <div>
            <div className="card m-5" style={{width: '18rem'}}>
            <h2 className="card-header text-center">Create a Playlist</h2>
            <div>
                {error.length > 0 ? <MessageDisplay key={error} error={error} /> : null}
            </div>
            <form onSubmit={handleSubmit} className="card-body">
                <div className="card-text form-group">
                    <label htmlFor="playlistName">Playlist name:</label>
                    <input
                        type="text"
                        className="form-control mb-2"
                        placeholder="Enter Playlist Name"
                        onChange={(event) => setPlaylistName(event.target.value)}
                        id="username"
                    />
                </div>
                <div className="card-text form-group">
                    <label htmlFor="description">Description:</label>
                    <input
                        type="text"
                        className="form-control mb-2"
                        placeholder="Enter Description"
                        onChange={(event) => setPlaylistTag(event.target.value)}
                        id="email"
                    />
                </div>
                <div className="card-text form-group">
                    <label htmlFor="tags">Tags:</label>
                    <input
                        type="text"
                        className="form-control mb-2"
                        placeholder="Enter Tags"
                        onChange={(event) => setPlaylistTag(event.target.value)}
                        id="email"
                    />
                </div>
                <div>
                    <button type="submit" className="btn btn-success mt-2">Create Playlist</button>
                </div>
            </form>
        </div>
        </div>
    );
}

export default AddPlaylist;