package learn.spotifyPlaylist.data.mappers;

import learn.spotifyPlaylist.models.Track;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackMapper implements RowMapper<Track> {

    @Override
    public Track mapRow(ResultSet resultSet, int i) throws SQLException {
        Track track = new Track();
        track.setTrackId(resultSet.getInt("track_id"));
        track.setTrackName(resultSet.getString("trackName"));
        track.setDuration(resultSet.getLong("duration_ms"));
        track.setArtistId(resultSet.getInt("artist_id"));
        track.setArtistName(resultSet.getString("artistName"));
        return track;
    }
}
