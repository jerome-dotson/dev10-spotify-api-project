package learn.spotifyPlaylist.data.mappers;

import learn.spotifyPlaylist.models.Collaborator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CollaboratorMapper implements RowMapper<Collaborator> {

    @Override
    public Collaborator mapRow(ResultSet resultSet, int i) throws SQLException {

        Collaborator collaborator = new Collaborator();

        collaborator.setAppUserId(resultSet.getInt("app_user_id"));
        collaborator.setPlaylistId(resultSet.getInt("playlist_id"));
        if (resultSet.getInt("accepted") == 1) {
            collaborator.setAccepted(true);
        }

        return collaborator;
    }
}

