package learn.spotifyPlaylist.data;

import learn.spotifyPlaylist.data.mappers.ImageMapper;
import learn.spotifyPlaylist.data.mappers.PlaylistMapper;

import learn.spotifyPlaylist.data.mappers.RoleMapper;
import learn.spotifyPlaylist.data.mappers.TagMapper;
import learn.spotifyPlaylist.data.mappers.UserPlaylistMapper;
import learn.spotifyPlaylist.models.*;

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

        //TODO: attach username of playlist creator
    }

    @Override
    @Transactional
    public Playlist findById(int playlistId) {

        final String sql = "select playlist_id, `name`, `description`, app_user_id from playlist where playlist_id = ?;";

        Playlist playlist = jdbcTemplate.query(sql, new PlaylistMapper(), playlistId).stream()
                .findFirst().orElse(null);

        if (playlist != null) {
            addImage(playlist);
            addTags(playlist);
            addCollaborators(playlist);
            addPlaylistCreator(playlist);
        }

        return playlist;

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

        Image playlistImage = jdbcTemplate.query(sql, new ImageMapper(), playlist.getPlaylistId()).stream()
                        .findFirst().orElse(null);


        playlist.setImage(playlistImage);
    }

    private void addTags(Playlist playlist) {

        final String sql = "select t.tag_id, t.content "
                + "from tag t "
                + "inner join tag_playlist tp on t.tag_id = tp.tag_id "
                + "inner join playlist p on tp.playlist_id = p.playlist_id "
                + "where p.playlist_id = ?;";

        List<Tag> tags = jdbcTemplate.query(sql, new TagMapper(), playlist.getPlaylistId());
        playlist.setTags(tags);
    }

    private void addCollaborators(Playlist playlist) {

        final String sql = "select up.app_user_id, up.playlist_id, up.accepted "
                + "from user_playlist up "
                + "inner join playlist p on up.playlist_id = p.playlist_id "
                + "inner join app_user au on up.app_user_id = au.app_user_id "
                + "where p.playlist_id = ?;";

        List<UserPlaylist> collaborators = jdbcTemplate.query(sql, new UserPlaylistMapper(), playlist.getPlaylistId());
        playlist.setCollaborators(collaborators);
    }
    private void addPlaylistCreator(Playlist playlist) {

        //first, get user roles
        final String sqlForRoles = "select ar.`name` "
                + "from user_role ur "
                + "inner join app_role ar on ur.app_role_id = ar.app_role_id "
                + "where ur.app_user_id = ?;";

        List<String> roles = jdbcTemplate.query(sqlForRoles, new RoleMapper(), playlist.getAppUserId());

        final String sql = "select au.app_user_id, au.first_name, au.last_name, au.username, au.password_hash, au.email, au.disabled " +
                "from app_user au " +
                "inner join playlist p on au.app_user_id = p.app_user_id " +
                "where p.playlist_id = ?;";

        AppUser playlistCreator = jdbcTemplate.query(sql, new AppUserMapper(roles), playlist.getPlaylistId()).stream()
                .findFirst().orElse(null);
        playlist.setAppUser(playlistCreator);

    }

//    private void addCollaborators(Playlist playlist) {
//
//        final String sql = "select au.username " +
//                "from user_playlist up " +
//                "inner join app_user au on up.app_user_id = au.app_user_id " +
//                "inner join playlist p on up.playlist_id = p.playlist_id " +
//                "where p.playlist_id = ?;";
//
//        List<AppUser> appUser = jdbcTemplate.query(sql, new AppUserMapper(), playlist.getPlaylistId());
//
//    }

}
