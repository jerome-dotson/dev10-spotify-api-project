import React, { useState } from "react";
import axios from "axios";

//need to npm install axios via terminal

function SongSearch() {

    const [searchKey, setSearchKey] = useState("");
    const [tracks, setTracks] = useState([]);

    //do we need to set up auth context here to get the token below in authorization?

    const searchTracks = async (e) => {
        e.preventDefault();

        const { data } = await axios.get("https://api.spotify.com/v1/search", {
            headers: {
                Authorization: `Bearer ${token}`
            },
            params: {
                q: searchKey,
                type: "track"
            }
        })
        setTracks(data.tracks.items);
    }

    const renderTracks = () => {
        return tracks.map(track => (
            <div key={track.id}>
                {track.name}
                {track.artist[0].name}
                {track.duration_ms}
            </div>
        ))
    }


    return (
        <div>
            <form onSubmit={searchTracks}>
                <input type="text" onChange={e => setSearchKey(e.target.value)} />
                <button type={"submit"}>Search</button>
            </form>
            {renderTracks()};
        </div>
    );
}

export default SongSearch;