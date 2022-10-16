import React from "react";
import { useState } from "react";
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
            params: searchKey
        });
        setPlaylists(data.playlists.items);
    };

    //clicking on playlist name or creator will take user to Playlist page, all important playlist information
    const renderPlaylists = () => {
        return playlists.map(playlist => (
            <Playlist key={playlist.playlist_id} playlistData={playlist} />
        ));
    };

    return (
        <div className="text-center">
        <div className="card text-center p-2 m-5" style={{width: '30rem'}}>
            <h1 className="card-header">Search Playlists</h1>
            <form onSubmit={searchPlaylists}>
                <input 
                type="text" 
                className="form-control m-3"
                style={{width: '25rem'}}
                onChange={e => setSearchKey(e.target.value)} />
                <button type={"submit"} className="btn btn-success">Search</button>
            </form>
            {renderPlaylists()}
        </div>
        </div>
    );
}

export default Playlists;