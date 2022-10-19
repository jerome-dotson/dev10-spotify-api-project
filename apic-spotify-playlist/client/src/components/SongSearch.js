
import axios from "axios";
import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";

function SongSearch() {

    const [spotifyToken, setSpotifyToken] = useState("");
    const [searchKey, setSearchKey] = useState("");
    const [songs, setSongs] = useState([]);


    const searchSongs = async (event) => {
        event.preventDefault();
        const init = {
            headers: {
                Authorization: `Bearer ${window.localStorage.getItem("current_spotify_access_token")}`
            },
            params: {
                q: searchKey,
                type: "track"
            }
        };

        const { data } = await axios.get("https://api.spotify.com/v1/search", init
        );
        // console.log("Start here.");
        // console.log(data.tracks.items);
        setSongs(data.tracks.items);
    }

    console.log(songs);

    return (

        <div className="container text-center">
            <div className="card text-center p-2 m-5" style={{ width: '30rem' }}>
                <h1 className="card-header">Song Search</h1>
                <form onSubmit={searchSongs}>
                    <input type="text" 
                    className="form-control m-3"
                    style={{ width: '25rem' }}
                    onChange={event => setSearchKey(event.target.value)} />
                    <button type={"submit"}>Search Songs</button>
                </form>
                {songs.map(song => <div key={song.id}>{song.name}{song.artist[0].name}{song.duration_ms}</div>)}
            </div>
        </div >
    )
}

export default SongSearch;