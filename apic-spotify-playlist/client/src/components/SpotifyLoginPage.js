import { useEffect } from "react";
import { useParams } from "react-router-dom";

function SpotifyLoginPage(){

    const code = (new URLSearchParams(window.location.search)).get("code");

    useEffect(() => {
        
        fetch("http://localhost:8080/api/spotify/callbackHandler", {
            method: "POST",
            headers: {
                "Authorization" : 
            }
        })
    }, [])

    return "Logged in page loading.";
}

export default SpotifyLoginPage;