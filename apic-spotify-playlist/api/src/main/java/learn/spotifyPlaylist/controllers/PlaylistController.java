package learn.spotifyPlaylist.controllers;

import learn.spotifyPlaylist.models.Playlist;
import learn.spotifyPlaylist.domain.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/playlist")
public class PlaylistController {

    private final PlaylistService service;

    public PlaylistController(PlaylistService service) {
        this.service = service;
    }

    @GetMapping
    public List<Playlist> findAll() {
        return service.findAll();
    }

    @GetMapping
    public Playlist findById(@PathVariable int playlistId){
        return service.findById(playlistId);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Playlist playlist) {
        Result<Playlist> result = service.add();
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED)
        }
        return ErrorResponse.build(result);
    }



}
