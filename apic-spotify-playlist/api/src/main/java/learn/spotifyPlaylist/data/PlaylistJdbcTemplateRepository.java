package learn.spotifyPlaylist.data;

import learn.spotifyPlaylist.data.mappers.ImageMapper;
import learn.spotifyPlaylist.data.mappers.PlaylistMapper;
import learn.spotifyPlaylist.models.Image;
import learn.spotifyPlaylist.models.Playlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class PlaylistJdbcTemplateRepository implements PlaylistRepository {

    private final JdbcTemplate jdbcTemplate;

    public PlaylistJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Playlist> findAll() {

        final String sql = "select playlist_id, `name`, `description`, app_user_id from playlist;";

        return jdbcTemplate.query(sql, new PlaylistMapper());

        //TODO: attach appropriate image, username, collaborators, and tags to each playlist
    }

    @Override
    public Playlist add(Playlist playlist) {

        final String sql = "insert into playlist (`name`, `description`, app_user_id) " +
                "values ( ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, playlist.getName());
                ps.setString(2, playlist.getDescription());
                ps.setInt(3, playlist.getAppUserId());
                return ps;
            }, keyHolder);

            if (rowsAffected <= 0) {
                return null;
            }

            playlist.setPlaylistId(keyHolder.getKey().intValue());
            return playlist;
    }

    @Override
    public boolean update(Playlist playlist) {

        final String sql = "update playlist set "
                + "name = ?, "
                + "description = ?, "
                + "app_user_id = ?, "
                + "where playlist_id = ?;";

        return jdbcTemplate.update(sql,
                playlist.getName(),
                playlist.getDescription(),
                playlist.getAppUserId(),
                playlist.getPlaylistId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int playlistId) {
        jdbcTemplate.update("delete from user_playlist where playlist_id = ?;", playlistId);
        jdbcTemplate.update("delete from track_playlist where playlist_id = ?;", playlistId);
        jdbcTemplate.update("delete from tag_playlist where playlist_id = ?;", playlistId);
        jdbcTemplate.update("delete from image_playlist where playlist_id = ?;", playlistId);

        return jdbcTemplate.update("delete from playlist where playlist_id = ?;", playlistId) > 0;
    }

    //TODO: create mappers for image and collaborators
    private void addImage(Playlist playlist) {

        final String sql = "select i.image_id, i.url, i.height, i.width "
                + "from image_playlist ip "
                + "inner join image i on ip.image_id = i.image_id "
                + "inner join playlist p on ip.playlist_id = p.playlist_id "
                + "where p.playlist_id = 2;";

        Image playlistImage = jdbcTemplate.query(sql, new ImageMapper(), playlist.getPlaylistId()).get(0);

        playlist.setImage(playlistImage);
    }

    private void addCollaborators(Playlist playlist) {



    }

}
