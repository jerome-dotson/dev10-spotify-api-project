import React from "react";
import { useState, useContext, useEffect } from "react";
import { useParams } from "react-router-dom";
import Songs from "./Songs";
import AuthContext from "../context/AuthContext";


//do we need to use props or pull from id params?
function PlaylistInfo() {

    const { id } = useParams();

    const [playlist, setPlaylist] = useState(null);

    // const [errors, setErrors] = useState([]);

    const auth = useContext(AuthContext);

    // const history = useHistory();

    useEffect(() => {
        fetch("http://localhost:8080/api/playlist/" + id)
            .then(
                response => {
                    if (response.status === 200) {
                        return response.json();
                    } else {
                        return response.json();
                    }
                }
            )
            .then(data => setPlaylist(data))
            .catch(err => console.log(err));
    },
        []);

    //display image, playlist name, creator username, number of accepted collaborators
    //list of tracks on playlist (song name, artist name, length)
    //tags
    //conditional rendering:
    //<Add to favorites(clone) button> and <go to playlist on spotify button>
    return (
        <div>
            <h2>Playlist Info</h2>
            {/* {errors.map((error, i) => (
        <Error key={i} msg={error} />
      ))} */}
            {/* <img src={playlist.image} alt="Playlist image" /> */}
            <h3>{playlist.name}</h3>
            <h3>{playlist.username}</h3>
            <h3>{playlist.collaborators.length} Collaborators</h3>
            <Songs></Songs>
            <p>{playlist.tags}</p>
            <div>
                {auth ?
                    <button>Add to Favorites</button>
                    : null}
            </div>
            <div>
                {auth ?
                    <button>Open Playlist in Spotify</button>
                    : null}
            </div>
        </div>
    );
}

export default PlaylistInfo;