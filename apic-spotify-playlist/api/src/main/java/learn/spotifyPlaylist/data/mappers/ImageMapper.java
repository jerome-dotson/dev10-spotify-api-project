package learn.spotifyPlaylist.data.mappers;

import learn.spotifyPlaylist.models.Image;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageMapper implements RowMapper<Image> {

    @Override
    public Image mapRow(ResultSet resultSet, int i) throws SQLException {

        Image image = new Image();
        image.setImageId(resultSet.getInt("image_id"));
        image.setUrl(resultSet.getString("url"));
        image.setHeight(resultSet.getInt("height"));
        image.setWidth(resultSet.getInt("width"));
        image.setPlaylistId(resultSet.getInt("playlist_id"));

        return image;
    }
}
