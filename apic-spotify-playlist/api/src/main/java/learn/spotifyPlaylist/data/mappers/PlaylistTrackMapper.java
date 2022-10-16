package learn.spotifyPlaylist.data.mappers;

import learn.spotifyPlaylist.models.PlaylistTrack;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistTrackMapper implements RowMapper<PlaylistTrack> {

    @Override
    public PlaylistTrack mapRow(ResultSet resultSet, int i) throws SQLException {
        PlaylistTrack playlistTrack = new PlaylistTrack();
        //basic playlist_track columns
//        playlistTrack.setTrackId(resultSet.getInt("track_id"));
//        playlistTrack.setPlaylistId(resultSet.getInt("playlist_id"));
//        playlistTrack.setAppUserId(resultSet.getInt("app_user_id"));

        //track_id, track name, duration, artist name, collaborator (person who added song)
        //select t.track_id, t.`name` as trackName, t.duration_ms, a.`name` as artistName, tp.app_user_id as collaborator
        playlistTrack.setTrackId(resultSet.getInt("track_id"));
        playlistTrack.setTrackName(resultSet.getString("trackName"));
        playlistTrack.setDuration(resultSet.getLong("duration_ms"));
        playlistTrack.setArtistName(resultSet.getString("artistName"));
        playlistTrack.setAppUserId(resultSet.getInt("collaborator"));
        playlistTrack.setPlaylistId(resultSet.getInt("playlist_id"));
        return playlistTrack;
    }

}
