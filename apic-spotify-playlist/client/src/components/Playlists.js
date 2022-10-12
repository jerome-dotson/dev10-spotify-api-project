import React from "react";
import { useState } from "react";

function Playlists() {

    const [searchKey, setSearchKey] = useState("");
    const [playlists, setPlaylists] = useState([]);

    const searchPlaylists = async (evt) => {
        evt.preventDefault();

        const {data} = await fetch("http://localhost:8080/playlists", {
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

    //render playlists function
    const renderPlaylists = () => {
        return playlists.map( playlist => (
            <div key={playlist.playlist_id} >
                {playlist.playlistName}
                {playlist.createdBy}
            </div>
        ))
    }

    return (
        <div>
        <form onSubmit={searchPlaylists}>
            <input type="text" onChange={ e => setSearchKey( e.target.value )} />
            <button type={"submit"}>Search</button>
        </form>
        {renderPlaylists()}
        </div>
    );
}

export default Playlists;