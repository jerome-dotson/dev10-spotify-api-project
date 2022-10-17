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
        track.setName(resultSet.getString("name"));
        track.setDuration(resultSet.getLong("duration_ms"));
        track.setArtist(resultSet.getString("artist"));
        track.setAppUserId(resultSet.getInt("app_user_id"));
        track.setPlaylistId(resultSet.getInt("playlist_id"));
        return track;
    }
}
