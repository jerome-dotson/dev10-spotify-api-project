import '../App.css';
import { React, useContext, useEffect, useState } from "react";
import axios from "axios";
import AuthContext from '../context/AuthContext';

function UserPage({ onSpotifyTokenUpdated, onLogout }) {
    const auth = useContext(AuthContext);


    const CLIENT_ID = "79a14f37fcfe47e9b518dacd49de5bef";
    const REDIRECT_URI = "http://localhost:3000/userpage";
    state = generateRandomString(16);
    const scopes = ["user-read-email", "playlist-read-private", "playlist-read-collaborative", "playlist-modify-private", "playlist-modify-public"];
    const AUTH_ENDPOINT = "https://accounts.spotify.com/authorize";
    const RESPONSE_TYPE = "token";
    //edit scope, possibly 

    const [searchKey, setSearchKey] = useState("");
    const [artists, setArtists] = useState([]);

    useEffect(() => {
        const hash = window.location.hash;
        let token = window.localStorage.getItem("spotifyToken");

        if (!auth.spotifyToken && hash) {
            token = hash.substring(1).split("&").find(element => element.startsWith("access_token")).split("=")[1];

            window.location.hash = "";
            window.localStorage.setItem("spotifyToken", token);
            onSpotifyTokenUpdated(token);

        }

        // setToken(token);

    }, []);


    const searchArtists = async (e) => {
        e.preventDefault();
        if (auth.spotifyToken) {
            const { data } = await axios.get("https://api.spotify.com/v1/search", {
                headers: {
                    Authorization: `Bearer ${auth.spotifyToken}`
                },
                params: {
                    q: searchKey,
                    type: "artist"
                }
            });

            console.log(data);
            setArtists(data.artists.items);
        }
    };

    const renderArtists = () => {
        return artists?.map(artist => (
            <div key={artist.id}>
                {artist.images.length ? <img width={"100%"} src={artist.images[0].url} alt="" /> : <div>No Image</div>}
                {artist.name}
            </div>
        ));
    };

    return (

        <div className="card m-5 container">
            <div className='card-header text-center mt-2 mb-3'>
                <h1>Account Information</h1>
            </div>
            <div className='row mb-2'>
                <div className='col-sm-6'>
                    <div className='card container'>
                        <div className='card-body'>
                            <div>
                                <h3>Username</h3>
                                <p>{auth.user.username}</p>
                            </div>
                            <div>
                                <h3>Name</h3>
                                <p>{auth.user.firstName} {auth.user.lastName}</p>
                            </div>
                            <div>
                                <h3>Email</h3>
                                <p>{auth.user.email}</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div className='col-sm-6'>
                    <div className='card container'>
                        <div className='card-body'>
                                   <header className="App-header">
                <h1>Spotify React</h1>
                {!auth.spotifyToken ?
                    <a href={`${AUTH_ENDPOINT}?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&scope=${scopes.join("%20")}&response_type=${RESPONSE_TYPE}`}>Login
                        to Spotify</a>
                    : <button onClick={onLogout}>Logout</button>}

                {auth.spotifyToken ?
                    <form onSubmit={searchArtists}>
                        <input type="text" onChange={e => setSearchKey(e.target.value)} />
                        <button type={"submit"}>Search</button>
                    </form>

                    : <h2>Please login</h2>
                }

                {renderArtists()}

            </header>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default UserPage;
