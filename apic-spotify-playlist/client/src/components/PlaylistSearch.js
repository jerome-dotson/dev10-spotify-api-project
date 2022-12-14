import React from "react";
import { useState, useContext } from "react";
import { Link } from "react-router-dom";
import Playlist from "./Playlist";
import AuthContext from "../context/AuthContext";

function PlaylistSearch() {

    const [searchKey, setSearchKey] = useState("");
    const [playlists, setPlaylists] = useState([]);

    const auth = useContext(AuthContext);



    //this is a question mark? are we setting up search correctly
    const searchPlaylists = async (evt) => {
        evt.preventDefault();

        const init = {
            method: "GET",
            headers: {
                Authorization: `Bearer ${auth.user.token}`,
            }
        };

        const response = await fetch("http://localhost:8080/api/playlist/search/" + searchKey, init);
        const data = await response.json();
        setPlaylists(data);
    };

    //clicking on playlist name or creator will take user to Playlist page, all important playlist information
    // const renderPlaylists = () => {
    //     return playlists.map((playlist, i) =>
    //         <div key={playlist.playlistId}>
    //             {playlist.name} &nbsp; &nbsp;
    //             {playlist.description} &nbsp; &nbsp;
    //             <button className="btn btn-info btn sm ms-1 me-2" onClick={() => addPlaylistClone(i)}>+</button>
    //         </div>);
    // };

    return (
        <div className="container text-center">
            <div className="card text-center p-2 m-5 specialCard" style={{ width: '50rem', display: "inline-block" }}>
                <h1 className="card-header">Search Playlists</h1>
                <form onSubmit={searchPlaylists}>
                    <input
                        type="text"
                        className="form-control m-3"
                        style={{ width: '25rem', display: "inline-block"}}
                        onChange={e => setSearchKey(e.target.value)} />
                    <button type={"submit"} className="btn btn-success">Search Playlists</button>
                </form>
                {playlists.map((playlist, i) =>
                    <div key={playlist.playlistId} className="card m-1">
                        {playlist.name} &nbsp; &nbsp;
                        {playlist.description} &nbsp; &nbsp;
                        <Link className="btn btn-info btn sm ms-1 me-2" to={`/playlist/${playlist.playlistId}`}>View Playlist</Link>
                    </div>)}
            </div>
        </div>
    );
}

export default PlaylistSearch;