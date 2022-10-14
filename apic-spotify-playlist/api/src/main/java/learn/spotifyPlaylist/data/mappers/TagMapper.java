package learn.spotifyPlaylist.data.mappers;

import learn.spotifyPlaylist.models.Tag;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {

        Tag tag = new Tag();
        tag.setTagId(rs.getInt("tag_id"));
        tag.setContent(rs.getString("content"));
        tag.setAppUserId(rs.getInt("app_user_id"));

        return tag;
    }
}
