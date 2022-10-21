import '../App.css';
import { React, useContext, useEffect, useState } from "react";
// import { useHistory } from 'react-router-dom';
import axios from "axios";
import AuthContext from '../context/AuthContext';
import { accessToken } from '../spotify';

function UserPage({ onSpotifyTokenUpdated /* onLogout */ }) {
    const auth = useContext(AuthContext);

    // const history = useHistory();

    // const CLIENT_ID = "79a14f37fcfe47e9b518dacd49de5bef";
    // const REDIRECT_URI = "http://localhost:3000/userpage";
    // const AUTH_ENDPOINT = "https://accounts.spotify.com/authorize";
    // const RESPONSE_TYPE = "token";

    const [searchKey, setSearchKey] = useState("");
    const [artists, setArtists] = useState([]);
    

    useEffect(() => {
        onSpotifyTokenUpdated(accessToken);

    
        // const fetchData = async () => {
        //   const { data } = await getCurrentUserProfile();
        //   setProfile(data);
        // };
    
        // catchErrors(fetchData());
      }, []);


    const searchArtists = async (e) => {
        e.preventDefault();
        if (accessToken) {
            const { data } = await axios.get("https://api.spotify.com/v1/search", {
                headers: {
                    Authorization: `Bearer ${accessToken}`
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
        //need to add buttons and functions to edit and delete account
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
                            {/* <div>
                                <h3>Name</h3>
                                <p>{auth.user.firstName} {auth.user.lastName}</p>
                            </div>
                            <div>
                                <h3>Email</h3>
                                <p>{auth.user.email}</p>
                            </div> */}
                        </div>
                    </div>
                </div>
                <div className='col-sm-6'>
                    <div className='card container'>
                        <div className='card-body text-center'>
                            <header className="App-header">
                                {!window.localStorage.getItem("current_spotify_access_token") ?
                                    <a href="http://localhost:8080/api/spotify/login" className="btn btn-primary">Login
                                        to Spotify</a>
                                    : "Logged In To Spotify" }

                                {auth.spotifyToken ?
                                    <form onSubmit={searchArtists}>
                                        <input type="text" 
                                        className="form-control"
                                        onChange={e => setSearchKey(e.target.value)} />
                                        <button type={"submit"} className="btn btn-success">Search</button>
                                    </form>

                                    : null
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
