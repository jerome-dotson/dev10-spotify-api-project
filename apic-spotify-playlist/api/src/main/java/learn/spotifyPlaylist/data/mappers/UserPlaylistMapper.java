package learn.spotifyPlaylist.data.mappers;

import learn.spotifyPlaylist.models.UserPlaylist;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserPlaylistMapper implements RowMapper<UserPlaylist> {

    @Override
    public UserPlaylist mapRow(ResultSet resultSet, int i) throws SQLException {

        UserPlaylist userPlaylist = new UserPlaylist();

        userPlaylist.setAppUserId(resultSet.getInt("app_user_id"));
        userPlaylist.setPlaylistId(resultSet.getInt("playlist_id"));
        if (resultSet.getInt("accepted") == 1) {
            userPlaylist.setAccepted(true);
        }

        return userPlaylist;
    }
}

