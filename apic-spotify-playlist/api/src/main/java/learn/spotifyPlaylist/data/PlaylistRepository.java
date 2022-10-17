package learn.spotifyPlaylist.data;

import learn.spotifyPlaylist.models.Playlist;
import learn.spotifyPlaylist.models.Tag;
import learn.spotifyPlaylist.models.Track;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlaylistRepository {
    List<Playlist> findAll();

    List<Playlist> findAllByUserId(int appUserId);

    List<Playlist> findCollaboratingPlaylists(int appUserId);

    List<Playlist> findPendingCollaboratingPlaylists(int appUserId);

    Playlist findById(int playlistId);

    Playlist add(Playlist playlist);

    public Tag addTagToDatabase(Tag tag);

    public boolean deleteTag(int tagId);

    @Transactional
    boolean deleteById(int playlistId);
}
