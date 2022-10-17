package learn.spotifyPlaylist.data.mappers;

import learn.spotifyPlaylist.models.Playlist;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistMapper implements RowMapper<Playlist> {

    @Override
    public Playlist mapRow(ResultSet resultSet, int i) throws SQLException {

        Playlist playlist = new Playlist();
        playlist.setPlaylistId(resultSet.getInt("playlist_id"));
        playlist.setName(resultSet.getString("name"));
        playlist.setDescription(resultSet.getString("description"));
        playlist.setAppUserId(resultSet.getInt("owner_id"));

        return playlist;
    }
}
