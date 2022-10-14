package learn.spotifyPlaylist.data;

import learn.spotifyPlaylist.models.Playlist;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlaylistRepository {
    List<Playlist> findAll();

    List<Playlist> findAllById(int appUserId);

    List<Playlist> findCollaboratingPlaylists(int appUserId);

    List<Playlist> findPendingCollaboratingPlaylists(int appUserId);

    Playlist findById(int playlistId);

    Playlist add(Playlist playlist);

    boolean update(Playlist playlist);

    @Transactional
    boolean deleteById(int playlistId);
}
