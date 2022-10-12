import React from "react";
import { useState } from "react";
import Playlist from "./Playlist";
import Playlist from "./Playlist";

function Playlists() {

    const [searchKey, setSearchKey] = useState("");
    const [playlists, setPlaylists] = useState([]);

    const searchPlaylists = async (evt) => {
        evt.preventDefault();

        const { data } = await fetch("http://localhost:8080/api/playlists", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
            params: searchKey,
            body: JSON.stringify({
                playlistName,
                createdBy
            })
        })
        setPlaylists(data.playlists.items);
    }

    //clicking on playlist name or creator will take user to Playlist page, all important playlist information
    const renderPlaylists = () => {
        return playlists.map(playlist => (
            <Playlist key={playlist.playlist_id} playlistData={playlist} />
        ))
    }

    return (
        <div>
            <form onSubmit={searchPlaylists}>
                <input type="text" onChange={e => setSearchKey(e.target.value)} />
                <button type={"submit"}>Search</button>
            </form>
            {renderPlaylists()}
        </div>
    );
}

export default Playlists;