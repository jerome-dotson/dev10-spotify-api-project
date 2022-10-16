package learn.spotifyPlaylist.data.mappers;

import learn.spotifyPlaylist.models.Playlist;
import learn.spotifyPlaylist.models.PlaylistTag;
import learn.spotifyPlaylist.models.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistTagMapper implements RowMapper<PlaylistTag> {

    @Override
    public PlaylistTag mapRow(ResultSet resultSet, int i) throws SQLException {
        PlaylistTag playlistTag = new PlaylistTag();
        playlistTag.setTagId(resultSet.getInt("tag_id"));
        playlistTag.setTagId(resultSet.getInt("playlist_id"));
        return playlistTag;
    }
}
