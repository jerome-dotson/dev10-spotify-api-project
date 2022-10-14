package learn.spotifyPlaylist.domain;

import learn.spotifyPlaylist.data.PlaylistRepository;
import learn.spotifyPlaylist.models.Playlist;
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
        Result<Playlist> result = validate(playlist);
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

    public Result<Playlist> update(Playlist playlist) {
        Result<Playlist> result = validate(playlist);
        if ( !result.isSuccess() ) {
            return result;
        }

        if ( playlist.getPlaylistId() <= 0 ) {
            result.addMessage( "playlistId must be set for update operation", ResultType.INVALID );
            return result;
        }

        if ( !repository.update(playlist) ) {
            String message = String.format( "playlistId: %s not found", playlist.getPlaylistId() );
            result.addMessage( message, ResultType.NOT_FOUND );
        }

        return result;
    }

    public boolean deleteById(int playlistId) {
        return repository.deleteById(playlistId);
    }

    private Result<Playlist> validate(Playlist playlist) {
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
}
