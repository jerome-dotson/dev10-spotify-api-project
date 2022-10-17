package learn.spotifyPlaylist.controllers;

import learn.spotifyPlaylist.domain.PlaylistService;
import learn.spotifyPlaylist.models.*;
import learn.spotifyPlaylist.domain.Result;
import learn.spotifyPlaylist.security.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/playlist")
public class PlaylistController {

    private final PlaylistService service;

    private final AppUserService userService;

    public PlaylistController( PlaylistService service, AppUserService userService ) {
        this.service = service;
        this.userService = userService;
    }

    //////////////////////////////////////////////////////////////////
    //Playlist requests
    //////////////////////////////////////////////////////////////////
//TODO: fix URLs for any place with a path variable
    @GetMapping
    public List<Playlist> findAll() {
        return service.findAll();
    }

    @GetMapping("/hosting/{appUserId}")
    public List<Playlist> findAllByUserId(@PathVariable int appUserId) {
        return service.findAllByUserId(appUserId);
    }

    @GetMapping("/collaborating/{appUserId}")
    public List<Playlist> findCollaboratingPlaylists(@PathVariable int appUserId) {
        return service.findCollaboratingPlaylists(appUserId);
    }

    @GetMapping("/invited/{appUserId}")
    public List<Playlist> findPendingCollaboratingPlaylists(@PathVariable int appUserId) {
        return service.findPendingCollaboratingPlaylists(appUserId);
    }

    @GetMapping("/{playlistId}")
    public Playlist findById( @PathVariable int playlistId ){
        return service.findById( playlistId );
    }

    @PostMapping
    public ResponseEntity<Object> add( @RequestBody Playlist playlist ) {
        Result<Playlist> result = service.add(playlist);
        if( result.isSuccess() ) {
            return new ResponseEntity<>( result.getPayload(), HttpStatus.CREATED );
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<Void> deleteById( @PathVariable int playlistId ) {
        if ( service.deleteById( playlistId )){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //////////////////////////////////////////////////////////////////
    //Tag requests
    //////////////////////////////////////////////////////////////////

    @PostMapping("/{playlistId}/tag")
    public ResponseEntity<Object> addTag(@RequestBody Map<String, String> tagNameHolder, @PathVariable int playlistId) {
        String tag = tagNameHolder.get("tagContent");
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser currentUser = (AppUser) userService.loadUserByUsername(username);
        Result<Tag> result = service.addOrRetrieveTag(tag, currentUser.getAppUserId());
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/tag/{tagId}")
    public ResponseEntity<Void> deleteTagById(@PathVariable int tagId) {
        if (service.deleteTag(tagId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //////////////////////////////////////////////////////////////////
    //Image requests
    //////////////////////////////////////////////////////////////////

//    @PostMapping
//    public ResponseEntity<Object> addImage(@RequestBody Image image) {
//        Result<Image> result = service.addImage(image);
//        if( result.isSuccess() ) {
//            return new ResponseEntity<>( result.getPayload(), HttpStatus.CREATED );
//        }
//        return ErrorResponse.build(result);
//    }

    //does image need a delete request if it already gets deleted when a playlist gets deleted?

    //////////////////////////////////////////////////////////////////
    //Track requests
    //////////////////////////////////////////////////////////////////

    @PostMapping("/{playlistId}/track")
    public ResponseEntity<Object> addTrack(@RequestBody Track track) {
        Result<Track> result = service.addTrack(track);
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/track/{trackId}")
    public ResponseEntity<Void> deleteTrack(@PathVariable int trackId) {
        if (service.deleteTrack(trackId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
