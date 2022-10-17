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

//    private final JdbcTemplate jdbcTemplate;
//
//    public TrackJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    public List<PlaylistTrack> findAllByPlaylist(Playlist playlist) {
//
//        final String sql = "select track_id, `name`, duration_ms, artist, app_user_id, playlist_id from track where playlist_id = ?;";
//
//        return jdbcTemplate.query(sql, new PlaylistTrackMapper(), playlist.getPlaylistId());
//    }
//
//    @Override
//    public Track addTrack(Track track) {
//
//        final String sql = "insert into track (`name`, duration_ms, artist, app_user_id, playlist_id) values "
//                + "(?, ?, ?, ?, ?),";
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        int rowsAffected = jdbcTemplate.update(connection -> {
//                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//                ps.setString(1, track.getName());
//                ps.setLong(2, track.getDuration());
//                ps.setString(3, track.getArtist());
//                ps.setInt(4, track.getAppUserId());
//                ps.setInt(5, track.getPlaylistId());
//                return ps;
//        }, keyHolder);
//
//        if (rowsAffected <= 0) {
//            return null;
//        }
//
//        return track;
//    }
//
//    @Override
//    public boolean deleteTrack(Track track) {
//        return jdbcTemplate.update("delete from track where track_id = ?;") > 0;
//    }

    //TODO: see if we can just use the playlist repo for all these methods
    //if it's getting too complicated, use this repo class for track methods

}
