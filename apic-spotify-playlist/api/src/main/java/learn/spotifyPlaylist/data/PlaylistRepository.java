package learn.spotifyPlaylist.data;

import learn.spotifyPlaylist.models.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlaylistRepository {
    List<Playlist> findAll();

    List<Playlist> findAllByUserId(int appUserId);

    List<Playlist> findCollaboratingPlaylists(int appUserId);

    List<Playlist> findPendingCollaboratingPlaylists(int appUserId);

    Playlist findById(int playlistId);

    Playlist add(Playlist playlist);

    Playlist clonePlaylist(Playlist playlist);

    Tag addTagToDatabase(String tag, int appUserId);

    boolean deleteTag(int tagId);

//    Image addImageToDatabase(Image image);

//    boolean deleteImage(int imageId);

    Track addTrackToDatabase(Track track, int playlistId, int appUserId);

    @Transactional
    boolean deleteTrack(int trackId);

    List<AppUser> findCollaborators(int appUserId); //appUserId to filter out

    List<AppUser> playlistCollaborators(int playlistId);

    boolean deleteCollaborator(int playlistId, int appUserId);

    @Transactional
    boolean deleteById(int playlistId);

    Tag findByContent(String tag);

    boolean sendInvite(Collaborator collaborator);

    boolean acceptInvite(Collaborator collaborator);

    boolean denyInvite(int playlistId, int appUserId);

    List<Playlist> searchPlaylistsByName(String playlistName);
}
