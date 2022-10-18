package learn.spotifyPlaylist.data;

import learn.spotifyPlaylist.models.Playlist;
import learn.spotifyPlaylist.models.PlaylistTag;
import learn.spotifyPlaylist.models.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PlaylistJdbcTemplateRepositoryTest {

    final static int NEXT_PLAYLIST_ID = 5;

    final static int NEXT_TAG_ID = 9;

    final static int NEXT_TAG_ID_TO_DELETE = 10;

    @Autowired
    PlaylistJdbcTemplateRepository repository;

    @Autowired
    GoodKnownState goodKnownState;

    @BeforeEach
    void setup() {
        goodKnownState.set();
    }

//    @Test
//    void shouldFindAll() {
//        List<Playlist> playlists = repository.findAll();
//        assertNotNull(playlists);
//
//        assertTrue(playlists.size() >= 3 && playlists.size() <= 6);
//    }
//
    @Test
    void shouldFindJazzyJazz() {
        Playlist playlist = repository.findById(1);
        assertEquals(1, playlist.getPlaylistId());
        assertEquals("Jazzy jazz", playlist.getName());
        assertEquals("Smooth, classic, and always fresh", playlist.getDescription());
        assertEquals(1, playlist.getAppUserId());
    }
//
//    @Test
//    void shouldReturnAllImageData() {
//        Playlist playlist = repository.findById(1);
//        assertEquals(1, playlist.getImage().getImageId());
//        assertEquals("https://placekitten.com/100/100", playlist.getImage().getUrl());
//        assertEquals(100, playlist.getImage().getHeight());
//        assertEquals(100, playlist.getImage().getWidth());
//    }
//
//    @Test
//    void shouldReturnUsernameOfPlaylistCreator() {
//        Playlist playlist = repository.findById(1);
//        assertEquals("AAyers", playlist.getAppUser().getUsername());
//    }
//
//    @Test
//    void shouldReturnCollaborators() {
//        Playlist playlist = repository.findById(1);
//        assertEquals(2, playlist.getCollaborators().size());
//    }
//
//    @Test
//    void shouldReturnTags() {
//        Playlist playlist = repository.findById(1);
////        assertEquals(1, playlist.getTags().size());
//        assertEquals("Jazz", playlist.getTags().get(0).getContent());
//    }
//
//    @Test
//    void shouldReturnAppUserIdSpecificPlaylists() {
//        List<Playlist> playlists = repository.findAllByUserId(1);
//        assertEquals(1, playlists.size());
//        assertEquals("Smooth, classic, and always fresh", playlists.get(0).getDescription());
//    }
//
//    @Test
//    void shouldReturnPlaylistsUserIsOnlyACollaboratorOn() {
//        List<Playlist> playlists = repository.findCollaboratingPlaylists(1);
//        assertEquals(1, playlists.size());
//        assertEquals("Jam rock", playlists.get(0).getName());
//        assertEquals("A fusion of rock and long-winded jam sessions", playlists.get(0).getDescription());
//        assertEquals(2, playlists.get(0).getAppUserId());
//    }
//
//    @Test
//    void shouldReturnPlaylistsUserHasPendingInvitesTo() {
//        List<Playlist> playlists = repository.findPendingCollaboratingPlaylists(2);
//        assertEquals(1, playlists.size());
//        assertEquals(1, playlists.get(0).getPlaylistId());
//        assertEquals(1, playlists.get(0).getAppUserId());
//        assertEquals("Smooth, classic, and always fresh", playlists.get(0).getDescription());
//    }
//
//    @Test
//    void shouldAdd() {
//        Playlist playlist = makePlaylist();
//        Playlist actual = repository.add(playlist);
//        assertNotNull(actual);
//        assertEquals(NEXT_PLAYLIST_ID, actual.getPlaylistId());
//    }
//
//    @Test
//    void shouldAddTag() {
//        Playlist playlist = repository.findById(1);
////        Tag actualTag = repository.addTagToDatabase(makeTag());
//
////        assertNotNull(actualTag);
////        assertEquals(NEXT_TAG_ID, actualTag.getTagId());
////        assertEquals("Smooth", actualTag.getContent());
////        assertEquals(1, actualTag.getAppUserId());
//
//        assertTrue(playlist.getTags().size() >= 1 && playlist.getTags().size() <= 3);
//        assertEquals("Smooth", playlist.getTags().get(1).getContent());
//        assertEquals(1, playlist.getTags().get(1).getAppUserId());
//    }
//
//    @Test
//    void shouldDeleteTag() {
//        Playlist playlist = repository.findById(1);
////        Tag actualTag = repository.addTagToDatabase(makeTag());
//
////        assertNotNull(actualTag);
////        assertEquals(NEXT_TAG_ID_TO_DELETE, actualTag.getTagId());
////        assertEquals("Emo", actualTag.getContent());
////        assertEquals(2, actualTag.getAppUserId());
////
////        assertTrue(repository.deleteTag(actualTag.getTagId()));
////        assertFalse(repository.deleteTag(actualTag.getTagId()));
//    }
//
//    @Test
//    void shouldDeletePlaylist() {
//        assertTrue(repository.deleteById(3));
//        assertFalse(repository.deleteById(3));
//    }
//
//    private Playlist makePlaylist() {
//        Playlist playlist = new Playlist();
//        playlist.setName("Silk Sonic");
//        playlist.setDescription("An evening with Silk Sonic");
//        playlist.setAppUserId(2);
//        return playlist;
//    }

    private Tag makeTag() {
        Tag tag = new Tag();
        tag.setContent("Smooth");
        tag.setAppUserId(1);

        return tag;
    }

    private Tag makeTagToDelete() {
        Tag tag = new Tag();
        tag.setContent("Emo");
        tag.setAppUserId(2);

        return tag;
    }

}
