package learn.spotifyPlaylist.data;

import learn.spotifyPlaylist.data.mappers.ImageMapper;
import learn.spotifyPlaylist.data.mappers.PlaylistMapper;
import learn.spotifyPlaylist.data.mappers.RolesMapper;
import learn.spotifyPlaylist.data.mappers.UserPlaylistMapper;
import learn.spotifyPlaylist.models.AppUser;
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

    private void addImage(Playlist playlist) {

        final String sql = "select i.image_id, i.url, i.height, i.width "
                + "from image_playlist ip "
                + "inner join image i on ip.image_id = i.image_id "
                + "inner join playlist p on ip.playlist_id = p.playlist_id "
                + "where p.playlist_id = ?;";

        Image playlistImage = jdbcTemplate.query(sql, new ImageMapper(), playlist.getPlaylistId()).get(0);

        playlist.setImage(playlistImage);
    }

    private void addCollaborators(Playlist playlist) {

        final String sql = "select up.app_user_id, up.playlist_id, up.accepted "
                + "from user_playlist up "
                + "inner join playlist p on up.playlist_id = p.playlist_id "
                + "inner join app_user au on up.app_user_id = au.app_user_id "
                + "where p.playlist_id = ?;";

        var collaborators = jdbcTemplate.query(sql, new UserPlaylistMapper(), playlist.getPlaylistId());
        playlist.setCollaborators(collaborators);
    }

    private void addPlaylistCreator(Playlist playlist) {

        final String sql = "select au.app_user_id, au.first_name, au.last_name, au.username, au.password_hash, au.email, au.disabled " +
                "from app_user au " +
                "inner join playlist p on au.app_user_id = p.app_user_id " +
                "where p.playlist_id = ?;";

        //TODO: find a way to bring roles into here

        addRolesToUser(playlist);

        AppUser playlistCreator = jdbcTemplate.query(sql, new AppUserMapper(), playlist.getPlaylistId()).get(0);
    }

    private List<String> addRolesToUser(Playlist playlist) {

        final String sql = "select ar.`name` "
                + "from user_role ur "
                + "inner join app_role ar on ur.app_role_id = ar.app_role_id "
                + "where ur.app_user_id = ?;";

        List<String> roles = jdbcTemplate.query(sql, new RolesMapper(), playlist.getAppUserId());

        return roles;
    }



}
