package learn.spotifyPlaylist.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/spotify")
public class SpotifyController {
    @Value("${SPOTIFY_CLIENT_ID}")
    private String CLIENT_ID;
    @Value("${SPOTIFY_CLIENT_SECRET}")
    private String CLIENT_SECRET;


//    app.get('/login', (req, res) => {
//    const state = generateRandomString(16);
//        res.cookie(stateKey, state);
//
//    const scope = 'user-read-private user-read-email playlist-read-private playlist-read-collaborative playlist-modify-private playlist-modify-public';
//
//    const queryParams = querystring.stringify({
//                client_id: CLIENT_ID,
//                response_type: 'code',
//                redirect_uri: REDIRECT_URI,
//                state: state,
//                scope: scope,
//    });
//
//        res.redirect(`https://accounts.spotify.com/authorize?${queryParams}`);
//    });

    @GetMapping("/login")
    void spotifyLogin(HttpServletResponse response) {

        String state = generateRandomString(16);
        String scope = "user-read-private user-read-email playlist-read-private playlist-read-collaborative playlist-modify-private playlist-modify-public";
        String spotifyUrl = "https://accounts.spotify.com/authorize?client_id=" + CLIENT_ID + "&response_type=code&" +
                "redirect_uri=http://localhost:3000/spotify/callback&state=" + state + "&scope=" + scope;

        response.setHeader("Location", spotifyUrl);
        response.setStatus(302);
        Cookie stateCookie = new Cookie("spotify_auth_state", state);
        response.addCookie(stateCookie);
    }


    @PostMapping("/callbackHandler")
    void callbackHandler(@RequestBody Map<String, String> codeHolder) {
        String authorizationString = CLIENT_ID + ":" + CLIENT_SECRET;
        String encoded = Base64.getEncoder().encodeToString(authorizationString.getBytes());
        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();
        bodyValues.add("grant_type", "authorization_code");
        bodyValues.add("code", codeHolder.get("code"));
        bodyValues.add("redirect_uri", "http://localhost:3000/spotify/callback");

        WebClient client = WebClient.builder()
                .baseUrl("https://accounts.spotify.com/api/token")
                .defaultHeaders(headers -> {
                    headers.add("Content-Type", "application/x-www-form-urlencoded");
                    headers.add("Authorization", "Basic " + encoded);
                })
                .build();
        String response = client.post()
                .body(BodyInserters.fromFormData(bodyValues))
                .retrieve().bodyToMono(String.class).block();
        System.out.println(response);
        //    .then(response => {
        //        if (response.status === 200) {
        //
        //          const { access_token, refresh_token, expires_in } = response.data;
        //
        //          const queryParams = querystring.stringify({
        //            access_token,
        //            refresh_token,
        //            expires_in,
        //          });
        //
        //          // redirect to react app
        //          res.redirect(`http://localhost:3000/userpage/?${queryParams}`);
        //          // pass along tokens in query params
        //
        //        } else {
        //          res.redirect(`/?${querystring.stringify({ error : "invalid_token"})}`);
        //        }
        //      })
        //      .catch(error => {
        //        res.send(error);
        //      });
        //});
    }


    private String generateRandomString(int length) {
        String text = "";
        String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < length; i++) {
            text += possible.charAt((int) Math.floor(Math.random() * possible.length()));
        }
        return text;
    }
}
