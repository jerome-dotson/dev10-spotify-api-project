import { useEffect, useContext } from "react";
import AuthContext from "../context/AuthContext";

function SpotifyLoginPage(){


    const code = (new URLSearchParams(window.location.search)).get("code");
    const auth = useContext(AuthContext);



    // console.log("Start here."); //Here for debugging


    useEffect(() => {

        if(!window.localStorage.getItem("current_spotify_access_token")){

        //Might have to turn this into async await 
        fetch("http://localhost:8080/api/spotify/callbackHandler", {
            method: "POST",
            headers: {
                "Authorization" : `Bearer ${auth.user.token}`,
                "Content-Type" : "application/json"
            },
            body: JSON.stringify({code})
        }).then(response => {
            console.log(response);
            if(response.status === 200){
                return response.json();
            }else{
                console.log(response);
                
            }
        }
        ).then( data => {
            const {access_token, refresh_token, expires_in} = data;
            // console.log("The value of token is: " + access_token);  Having problems because of strict mode?
            window.localStorage.setItem("current_spotify_access_token", access_token);
            window.localStorage.setItem("current_spotify_refresh_token", refresh_token);
            window.localStorage.setItem("current_spotify_token_time_to_expiration", expires_in);
            window.localStorage.setItem("current_spotify_token_creation_time", String(Date.now()));
    
        }



        );}
    }, []);

    return "Logged in page loading.";
}

export default SpotifyLoginPage;