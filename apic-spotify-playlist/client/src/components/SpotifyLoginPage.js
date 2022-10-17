import { useEffect, useContext } from "react";
import AuthContext from "../context/AuthContext";

function SpotifyLoginPage(){

    const code = (new URLSearchParams(window.location.search)).get("code");
    const auth = useContext(AuthContext);


    useEffect(() => {

        
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
        ).then(data =>{

            // {"access_token":"BQA6eFKdfNKm_HIGfsBgEhcAqgOLGV-nCWr1q9ZhXn2llDiSSeceIfALGJqBmXNa5b81l77fti3ndVAR-bQ5xLYe2nID2ekUDaszV7UKLm9pVMORXQQgeGICoTYsSuyUTMI0RreLnmcY8587FVqSSnJjH1QwHvAigEW-Kq4v3I-_n0IEpS44NKEOOrxrE21iA3UiCbOUE_RlTTDzF5HaPm_4u87V2gLhhbJ9oxyumVKdiqibsfpSJigWf3jWt_IbB4obNdy-0JhAFlCozZF0zV_IJk8S1Q","token_type":"Bearer","expires_in":3600,"refresh_token":"AQB7vIwp96yxF9qLRZdRazebz4TNjhxqGbtJV3DMiIP1zr1O_CKmCuK_fWJ1wENm4u-IFpAFJpI63EEOJ1mtZa25EeyGsSu5iy8U95C6V5lR2JftptAIorbx1x2xwo_rB8s","scope":"playlist-read-private playlist-read-collaborative playlist-modify-private playlist-modify-public user-read-email user-read-private"}

        });
    }, []);

    return "Logged in page loading.";
}

export default SpotifyLoginPage;