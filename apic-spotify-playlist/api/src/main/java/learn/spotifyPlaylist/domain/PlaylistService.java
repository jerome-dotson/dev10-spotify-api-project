package learn.spotifyPlaylist.domain;

import learn.spotifyPlaylist.data.PlaylistRepository;
import learn.spotifyPlaylist.models.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository repository;

    public PlaylistService(PlaylistRepository repository) {
        this.repository = repository;
    }

    public List<Playlist> findAll() {
        return repository.findAll();
    }

    public List<Playlist> findAllByUserId(int appUserId) {
        return repository.findAllByUserId(appUserId);
    }

    public List<Playlist> findCollaboratingPlaylists(int appUserId) {
        return repository.findCollaboratingPlaylists(appUserId);
    }

    public List<Playlist> findPendingCollaboratingPlaylists(int appUserId) {
        return repository.findPendingCollaboratingPlaylists(appUserId);
    }

    public Playlist findById(int playlistId) {
        return repository.findById(playlistId);
    }

    public Result<Playlist> add(Playlist playlist) {
        Result<Playlist> result = validatePlaylist(playlist);
        if ( !result.isSuccess() ) {
            return result;
        }

        if ( playlist.getPlaylistId() != 0 ) {
            result.addMessage( "playlistId cannot be set for add operation", ResultType.INVALID );
            return result;
        }

        playlist = repository.add(playlist);
        result.setPayload(playlist);
        return result;
    }

    public boolean deleteById(int playlistId) {
        return repository.deleteById(playlistId);
    }

    private Result<Playlist> validatePlaylist(Playlist playlist) {
        Result<Playlist> result = new Result<>();
        if ( playlist == null ) {
            result.addMessage("playlist cannot be null", ResultType.INVALID);
            return result;
        }

        if ( playlist.getName() == null || playlist.getName().isBlank() ) {
            result.addMessage("playlist name cannot be null or blank", ResultType.INVALID);
        }

        return result;
    }


//    public Result<Playlist> update(Playlist playlist) {
//        Result<Playlist> result = validate(playlist);
//        if ( !result.isSuccess() ) {
//            return result;
//        }
//
//        if ( playlist.getPlaylistId() <= 0 ) {
//            result.addMessage( "playlistId must be set for update operation", ResultType.INVALID );
//            return result;
//        }
//
//        if ( !repository.update(playlist) ) {
//            String message = String.format( "playlistId: %s not found", playlist.getPlaylistId() );
//            result.addMessage( message, ResultType.NOT_FOUND );
//        }
//
//        return result;
//    }

    //////////////////////////////////////////////////////////////////
    //everything Tag related
    //////////////////////////////////////////////////////////////////

    public Result<Tag> addOrRetrieveTag(String tag, int appUserId) {
        Result <Tag> result = validateTag(tag);
        if (!result.isSuccess()) {
            return result;
        }

        Tag existingTag = repository.findByContent(tag);

        if (existingTag == null) {
            existingTag = repository.addTagToDatabase(tag, appUserId);
        }

        result.setPayload(existingTag);
        return result;
    }

    public boolean deleteTag(int tagId) {
        return repository.deleteTag(tagId);
    }

    private Result<Tag> validateTag(String tag) {
        Result<Tag> result = new Result<>();

        if(tag == null || tag.isBlank()) {
            result.addMessage("tag content cannot be null or blank", ResultType.INVALID);
        }

        return result;
    }

    //////////////////////////////////////////////////////////////////
    //everything Image related
    //////////////////////////////////////////////////////////////////

//    public Result<Image> addImage(Image image) {
//        Result<Image> result = validateImage(image);
//        if (!result.isSuccess()) {
//            return result;
//        }
//
//        if (image.getImageId() != 0) {
//            result.addMessage("imageId cannot be set for add operation", ResultType.INVALID);
//            return result;
//        }
//
//        image = repository.addImageToDatabase(image);
//        result.setPayload(image);
//        return result;
//    }

    //no need for delete image method since image will be deleted once playlist gets deleted ?

    private Result<Image> validateImage(Image image) {
        Result<Image> result = new Result<>();

        if(image.getUrl() == null || image.getUrl().isBlank()) {
            result.addMessage("image url cannot be cannot be null or blank", ResultType.INVALID);
        }

        return result;
    }

    //////////////////////////////////////////////////////////////////
    //everything Image related
    //////////////////////////////////////////////////////////////////

    public Result<Track> addTrack(Track track, int playlistId, int appUserId) {
        Result<Track> result = validateTrack(track);
        if (!result.isSuccess()) {
            return result;
        }

        if (track.getTrackId() != 0) {
            result.addMessage("trackId cannot be set for add operation", ResultType.INVALID);
            return result;
        }

        track = repository.addTrackToDatabase(track, playlistId, appUserId);
        result.setPayload(track);
        return result;
    }

    public boolean deleteTrack(int trackId) {
        return repository.deleteTrack(trackId);
    }
    private Result<Track> validateTrack(Track track) {
        Result<Track> result = new Result<>();

        if (track.getName() == null || track.getName().isBlank()) {
            result.addMessage("track name cannot be null or blank", ResultType.INVALID);
        }

        return result;
    }

    //////////////////////////////////////////////////////////////////
    // playlist invites
    //////////////////////////////////////////////////////////////////

    public boolean sendInvite(Collaborator collaborator) {
        return repository.sendInvite(collaborator);
    }

    public Result<Collaborator> acceptInvite(Collaborator collaborator) {
        Result<Collaborator> result = new Result<>();

        if (collaborator.getPlaylistId() <= 0) {
            result.addMessage("playlistId not found", ResultType.INVALID);
            return result;
        }

        if (collaborator.getAppUserId() <= 0) {
            result.addMessage("playlistId not found", ResultType.INVALID);
            return result;
        }

        if (!repository.acceptInvite(collaborator)) {
            result.addMessage("unable to accept invite", ResultType.INVALID);
        }

        return result;
    }

    public boolean denyInvite(int playlistId, int appUserId) {
        return repository.denyInvite(playlistId, appUserId);
    }

    //////////////////////////////////////////////////////////////////
    //Collaborators
    //////////////////////////////////////////////////////////////////

    public List<AppUser> findCollaborators(int appUserId) {
        return repository.findCollaborators(appUserId);
    }
}
