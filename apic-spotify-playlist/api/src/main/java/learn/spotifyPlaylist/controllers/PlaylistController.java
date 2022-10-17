package learn.spotifyPlaylist.controllers;

import learn.spotifyPlaylist.domain.PlaylistService;
import learn.spotifyPlaylist.models.Image;
import learn.spotifyPlaylist.models.Playlist;
import learn.spotifyPlaylist.domain.Result;
import learn.spotifyPlaylist.models.Tag;
import learn.spotifyPlaylist.models.Track;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/playlist")
public class PlaylistController {

    private final PlaylistService service;

    public PlaylistController( PlaylistService service ) {
        this.service = service;
    }

    //////////////////////////////////////////////////////////////////
    //Playlist requests
    //////////////////////////////////////////////////////////////////

    @GetMapping
    public List<Playlist> findAll() {
        return service.findAll();
    }

    @GetMapping("/hosting/appUserId")
    public List<Playlist> findAllByUserId(@PathVariable int appUserId) {
        return service.findAllByUserId(appUserId);
    }

    @GetMapping("/collaborating/appUserId")
    public List<Playlist> findCollaboratingPlaylists(@PathVariable int appUserId) {
        return service.findCollaboratingPlaylists(appUserId);
    }

    @GetMapping("/invited/appUserId")
    public List<Playlist> findPendingCollaboratingPlaylists(@PathVariable int appUserId) {
        return service.findPendingCollaboratingPlaylists(appUserId);
    }

    @GetMapping("/playlistId")
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

    @DeleteMapping("/playlistId")
    public ResponseEntity<Void> deleteById( @PathVariable int playlistId ) {
        if ( service.deleteById( playlistId )){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //////////////////////////////////////////////////////////////////
    //Tag requests
    //////////////////////////////////////////////////////////////////

//    @PostMapping
//    public ResponseEntity<Object> addTag(@RequestBody Tag tag) {
//        Result<Tag> result = service.addTag(tag);
//        if(result.isSuccess()) {
//            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
//        }
//        return ErrorResponse.build(result);
//    }

    @DeleteMapping("/tag/tagId")
    public ResponseEntity<Void> deleteTagById(@PathVariable int tagId) {
        if (service.deleteTag(tagId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //////////////////////////////////////////////////////////////////
    //Image requests
    //////////////////////////////////////////////////////////////////



    //does image need a delete request if it already gets deleted when a playlist gets deleted?

    //////////////////////////////////////////////////////////////////
    //Track requests
    //////////////////////////////////////////////////////////////////

//    @PostMapping
//    public ResponseEntity<Object> addTrack(@RequestBody Track track) {
//        Result<Track> result = service.addTrack(track);
//        if(result.isSuccess()) {
//            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
//        }
//        return ErrorResponse.build(result);
//    }

    @DeleteMapping("/track/trackId")
    public ResponseEntity<Void> deleteTrack(@PathVariable int trackId) {
        if (service.deleteTrack(trackId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
