
import axios from "axios";
import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";

function SongSearch() {

    const [spotifyToken, setSpotifyToken] = useState("");
    const [searchKey, setSearchKey] = useState("");
    const [songs, setSongs] = useState([]);


    useEffect(() => {




    }

        , []);


    const searchSongs = async (event) => {
        event.preventDefault();
        const { data } = await axios.get("https://api.spotify.com/v1/search", {
            headers: {
                Authorization: `Bearer ${window.localStorage.getItem("current_spotify_access_token")}`
            },
            params: {
                q: searchKey,
                type: "track"
            }
        });
        // console.log("Start here.");
       console.log(data.tracks.items);
        setSongs(data.tracks.items);
    }

    const renderSongs = () => {
        // console.log(songs);
        return songs.map(song => {
            <div key={song.id}>
                This is text{song.album}
                {song}
            </div>
        });
    };


    return (

        <div>
            <h1>Song Search</h1>
            <form onSubmit={searchSongs}>
                <input type="text" onChange={event => setSearchKey(event.target.value)} />
                <button type={"submit"}>Search Songs</button>
            </form>
            {renderSongs()}
        </div>
    )
}

export default SongSearch;