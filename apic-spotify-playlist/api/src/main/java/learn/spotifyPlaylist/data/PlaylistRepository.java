package learn.spotifyPlaylist.data;

import learn.spotifyPlaylist.models.Image;
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

    Tag addTagToDatabase(String tag, int appUserId);

    boolean deleteTag(int tagId);

    Image addImageToDatabase(Image image);

//    boolean deleteImage(int imageId);

    Track addTrackToDatabase(Track track);

    boolean deleteTrack(int trackId);

    @Transactional
    boolean deleteById(int playlistId);

    Tag findByContent(String tag);
}
