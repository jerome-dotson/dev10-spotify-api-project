import React, { useState, useContext, Link } from "react";
import axios from "axios";
import AuthContext from "../context/AuthContext";
import { accessToken } from "../spotify";

//need to npm install axios via terminal

const DEFAULT_TRACK = {
    trackId: 0,
    trackName: "",
    artistName: "",
    appUserId: "",
    playlistId: "",
}; 

function SongSearch(playlist) {


    const auth = useContext(AuthContext);

    const [searchKey, setSearchKey] = useState("");
    const [tracks, setTracks] = useState([]);
    const [trackToAdd, setTrackToAdd] = useState(DEFAULT_TRACK);

    //do we need to set up auth context here to get the token below in authorization?

    const searchTracks = async (e) => {
        e.preventDefault();

        const { data } = await axios.get("https://api.spotify.com/v1/search", {
            headers: {
                Authorization: `Bearer ${accessToken}`,
               "content-type" : "application/json"
            },
            params: {
                q: searchKey,
                type: "track"
            }
        });
        setTracks(data.tracks.items);
    };

    function addTrackToPlaylist(track) {

        // const trackToAdd = {
        //     trackId: 0,
        //     trackName: {track.name},
        //     artistName: "",
        //     appUserId: "",
        //     playlistId: "",
        // }

        //add song to our database from spotify api via trackController method (if track exists in our database, do not add)
        //add song to playlist from our database via playlistController or trackController method

        // fetch("http://localhost:8080/api/track/add", {
        //     method: "POST",
        //     headers: {
        //         "Content-Type": "application/json",
        //         Accept: "application/json",
        //         Authorization: `Bearer ${auth.user.token}`,
        // },
        // body: JSON.stringify(updatedSighting),    

        // })

        
    }

    const renderTracks = () => {
        return tracks.map(track => (
            <div key={track.id}>
                {track.name}
                {track.artist[0].name}
                {track.duration_ms}
                <button className="btn btn-success ms-1 me-2" onClick={addTrackToPlaylist(track)}>+</button>
            </div>

        ));
    };
    console.log(auth.spotifyToken);


    return (
        <div>
            <form onSubmit={searchTracks}>
                <input type="text" onChange={e => setSearchKey(e.target.value)} />
                <button type={"submit"}>Search</button>
            </form>
            {renderTracks()};
            <div>
                <Link className="btn btn-warning" to={`/playlist/${playlist.playlistData.playlist_id}`}>Return to Playlist</Link>
            </div>
        </div>
    );
}

export default SongSearch;