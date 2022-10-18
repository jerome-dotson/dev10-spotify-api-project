package learn.spotifyPlaylist.data;

import learn.spotifyPlaylist.models.Playlist;
import learn.spotifyPlaylist.models.PlaylistTrack;
import learn.spotifyPlaylist.models.SpotifyTrack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TrackJdbcTemplateRepositoryTest {

    final static int NEXT_TRACK_ID = 11;

    @Autowired
    TrackJdbcTemplateRepository repository;

    @Autowired
    GoodKnownState goodKnownState;

    @BeforeEach
    void setup() {
        goodKnownState.set();
    }

    @Test
    void shouldReturnCorrectPlaylistTracksFromPlaylistJamRock() {
        Playlist playlist = makePlaylist();
//        List<PlaylistTrack> actual = repository.findAllByPlaylist(makePlaylist());
//
//        assertNotNull(actual);
//        assertEquals(6, actual.size());


    }

    @Test
    void shouldAddTrackToDatabaseFromSpotifyTrack() {
        SpotifyTrack spotifyTrack = makeSpotifyTrack();
//        Track actual = repository.addTrack(spotifyTrack);

//        assertNotNull(actual);
//        assertEquals(11, actual.getTrackId());
    }

    private Playlist makePlaylist() {
        Playlist playlist = new Playlist();
        playlist.setPlaylistId(2);
        playlist.setName("Jam rock");
        playlist.setDescription("A fusion of rock and long-winded jam sessions");
        playlist.setAppUserId(2);
        return playlist;
    }
    private SpotifyTrack makeSpotifyTrack() {
        SpotifyTrack spotifyTrack = new SpotifyTrack();
        spotifyTrack.setTrackName("Put On a Smile");
        spotifyTrack.setArtistName("Silk Sonic");
        spotifyTrack.setDuration(10000);
        return spotifyTrack;
    }
}
