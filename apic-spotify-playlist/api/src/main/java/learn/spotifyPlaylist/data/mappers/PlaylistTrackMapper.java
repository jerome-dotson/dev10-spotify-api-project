package learn.spotifyPlaylist.data.mappers;

import learn.spotifyPlaylist.models.PlaylistTrack;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistTrackMapper implements RowMapper<PlaylistTrack> {

    @Override
    public PlaylistTrack mapRow(ResultSet resultSet, int i) throws SQLException {
        PlaylistTrack playlistTrack = new PlaylistTrack();
        playlistTrack.setTrackId(resultSet.getInt("track_id"));
        playlistTrack.setPlaylistId(resultSet.getInt("playlist_id"));
        playlistTrack.setAppUserId(resultSet.getInt("app_user_id"));
        return playlistTrack;
    }

    //TODO: revise playlist repo so you can delete this class
}
