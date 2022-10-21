import { useLocation, useParams } from "react-router-dom";
import axios from "axios";
import { useState, useContext } from "react";
import { useHistory } from "react-router-dom";
import AuthContext from "../context/AuthContext";

function SongSearch() {

    const [spotifyToken, setSpotifyToken] = useState("");
    const [searchKey, setSearchKey] = useState("");
    const [songs, setSongs] = useState([]);
    const location = useLocation();
    const from = location.state;
    const history = useHistory();
    const auth = useContext(AuthContext);
    const [playlistId, setPlaylistId] = useState(from);
    const { id } = useParams();


    const searchSongs = async (event) => {
        event.preventDefault();
        const init = {
            headers: {
                Authorization: `Bearer BQA7pgZFV94Obc6qJ38ztYBY4tGG7I3QkPu4FZ9VjNT_Llhra3Xrf2cFqAlPdFJweb_vGR_Vhdcb-XQ1l6aaVToLQGMX05rN8WcTs0FqliU6I7sOKE-DqLknob4ACMEG5T-VNLBiHw2nUis_dAt15MIfyKPFIIu2SJWs384b41Vr3HhoD-Qs0xEhtkZQSfY2_h5i0rIfjdA8EurfrcvsBgBtHAvdPq7Q8ywwoc-df645WpKOIPCdkzw9iRNaA5OIrJTPAfjesuizmtZ75Ek_UiTMG4pmzQ`
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

    function addSong(i) {

        const toSave = songs[i];

        const init = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                Authorization: `Bearer ${auth.user.token}`
            },
            body: JSON.stringify({
                name: toSave.name,
                artist: toSave.artists[0].name,
                duration: toSave.duration_ms
            })
        };

        fetch("http://localhost:8080/api/playlist/" + id + "/track", init)
            .then(response => {
                if (response.status === 201) {
                    console.log(response.status);
                    history.push("/playlist/" + id)
                } else {
                    console.log(response.status)
                }
            })
    }

    function msToMinSec(millis) {
        var minutes = Math.floor(millis / 60000);
        var seconds = ((millis % 60000) / 1000).toFixed(0);
        return (
            seconds == 60 ?
                (minutes + 1) + ":00" :
                minutes + ":" + (seconds < 10 ? "0" : "") + seconds
        );
    }

    function returnToPlaylist(evt) {
        evt.preventDefault();
        history.push("/playlist/" + id)
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
                    <button type={"submit"} className="btn btn-success m-1">Search Songs</button>
                    <button className="btn btn-warning" onClick={returnToPlaylist}>Return to Playlist</button>
                </form>
                {songs.map((song, i) => 
                <div key={song.id} className="card m-1" style={{ display: 'inline-block', width: '100%' }}>
                    {song.name} &nbsp; &nbsp;
                    {song.artists[0].name} &nbsp; &nbsp;
                    {msToMinSec(song.duration_ms)} &nbsp; &nbsp;
                    <button className="btn btn-info btn-sm" onClick={() => addSong(i)}>Add Song</button>
                </div>)}
            </div>
        </div >
    )
}

export default SongSearch;