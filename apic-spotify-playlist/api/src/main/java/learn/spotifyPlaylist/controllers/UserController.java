package learn.spotifyPlaylist.controllers;

import learn.spotifyPlaylist.models.AppUser;
import learn.spotifyPlaylist.security.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    AppUserService userService;

    @GetMapping("/search/{username}")
    List<AppUser> searchUsersByUsername(@PathVariable String username) {
        List<AppUser> matchingUsers = userService.searchUsersByUsername(username);
        return matchingUsers;
    }


}
