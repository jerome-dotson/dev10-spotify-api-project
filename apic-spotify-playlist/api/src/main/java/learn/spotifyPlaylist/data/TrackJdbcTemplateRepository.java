package learn.spotifyPlaylist.data;

import learn.spotifyPlaylist.data.mappers.PlaylistTrackMapper;
import learn.spotifyPlaylist.models.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TrackJdbcTemplateRepository implements TrackRepository {

    private final JdbcTemplate jdbcTemplate;

    public TrackJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PlaylistTrack> findAllByPlaylist(Playlist playlist) {
        //this sql query should probably also return app_user_id(for who added which songs)
        // and artist name
        final String sql = "select t.track_id, t.`name` as trackName, t.duration_ms, a.`name` as artistName, tp.app_user_id as collaborator, tp.playlist_id " +
                "from track t " +
                "inner join track_artist ta on t.track_id = ta.track_id " +
                "inner join artist a on ta.artist_id = a.artist_id " +
                "inner join track_playlist tp on t.track_id = tp.track_id " +
                "where tp.playlist_id = ?;";

        List<PlaylistTrack> playlistTracks = jdbcTemplate.query(sql, new PlaylistTrackMapper(), playlist.getPlaylistId());

        return playlistTracks;
    }

    @Override
    public Track addTrack(SpotifyTrack spotifyTrack) {
        //when adding a track, we have to add song name, artist name, and duration to the database

        //a fully hydrated Track object should have trackId, trackName, duration, artistId, artistName
        //instantiating a new track to return and hydrating along the way
        Track track = new Track();

        track.setTrackName(spotifyTrack.getTrackName());
        track.setDuration(spotifyTrack.getDuration());
        track.setArtistName(spotifyTrack.getArtistName());

        //add the track to the database
        final String sqlForTrack = "insert into track (`name`, duration_ms) values (?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlForTrack, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, spotifyTrack.getTrackName());
            ps.setLong(2, spotifyTrack.getDuration());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        track.setTrackId(keyHolder.getKey().intValue());

        //then add the artist to the database
        Artist artist = addArtist(track);

        //finish hydrating the Track object with artistId
        track.setArtistId(artist.getArtistId());

        //finally, update the track_artist table
        if (!updateTrackArtist(track)) {
            return null;
        }

        //TODO: figure out how to update track_playlist table
        //how do we know which playlist we're in and which user added the song?
        return track;
    }

    @Override
    @Transactional
    public boolean deleteTrack(Track track) {
        //delete from all bridge tables
        //TODO: still need to delete from trackPlaylist

        // @Override
        //    @Transactional
        //    public boolean deleteById(int playlistId) {
        //        jdbcTemplate.update("delete from user_playlist where playlist_id = ?;", playlistId);
        //        jdbcTemplate.update("delete from track_playlist where playlist_id = ?;", playlistId);
        //        jdbcTemplate.update("delete from tag_playlist where playlist_id = ?;", playlistId);
        //        jdbcTemplate.update("delete from image_playlist where playlist_id = ?;", playlistId);
        //
        //        return jdbcTemplate.update("delete from playlist where playlist_id = ?;", playlistId) > 0;
        //    }

//        jdbcTemplate.update()
        return false;
    }

    private Artist addArtist(Track track) {
        //add artist to the database
        //then add the artist to the database

        Artist artist = new Artist();

        artist.setArtistName(track.getArtistName());

        final String sql = "insert into artist (`name`) value (?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, track.getArtistName());
            return ps;
        }, keyHolder);

        if(rowsAffected <= 0) {
            return null;
        }

        artist.setArtistId(keyHolder.getKey().intValue());
        return artist;
    }

    private boolean updateTrackArtist(Track track) {
        //then add the data to the track_artist bridge table
        final String sql = "insert into track_artist (track_id, artist_id) values (?, ?);";

        return jdbcTemplate.update(sql,
                track.getTrackId(),
                track.getArtistId()) > 0;
    }


}
