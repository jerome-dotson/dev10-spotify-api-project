package learn.spotifyPlaylist.data;

import learn.spotifyPlaylist.data.mappers.*;

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

    //////////////////////////////////////////////////////////////////
    //find methods returning the 3 different lists of playlists, in addition to findAll()
    //////////////////////////////////////////////////////////////////

    @Override
    public List<Playlist> findAll() {

        final String sql = "select playlist_id, `name`, `description`, owner_id from playlist;";

        List<Playlist> all = jdbcTemplate.query(sql, new PlaylistMapper());
        all.forEach(p -> addPlaylistCreator(p)); //also returning username by adding playlist creator

        return all;
    }

    @Override
    public List<Playlist> findAllByUserId(int appUserId) {

        final String sql = "select playlist_id, `name`, `description`, owner_id from playlist where owner_id = ?;";

        List<Playlist> playlists = jdbcTemplate.query(sql, new PlaylistMapper(), appUserId);
        playlists.forEach(p -> addPlaylistCreator(p));

        return playlists;
    }

    @Override
    public List<Playlist> findCollaboratingPlaylists(int appUserId) {

        final String sql = "select p.playlist_id, p.`name`, p.`description`, p.owner_id "
                + "from playlist p "
                + "inner join collaborator up on p.playlist_id = up.playlist_id "
                + "where up.app_user_id = ? and up.accepted = 1 and p.owner_id != up.app_user_id;";
        //also filters out playlists they actually created
        //so the playlists returned are ones that they're ONLY a collaborator on, NOT the creator

        List<Playlist> playlists = jdbcTemplate.query(sql, new PlaylistMapper(), appUserId);
        playlists.forEach(p -> addPlaylistCreator(p));

        return playlists;
    }

    @Override
    public List<Playlist> findPendingCollaboratingPlaylists(int appUserId) {

        final String sql = "select p.playlist_id, p.`name`, p.`description`, p.owner_id "
                + "from playlist p "
                + "inner join collaborator up on p.playlist_id = up.playlist_id "
                + "where up.app_user_id = ? and up.accepted = 0 and p.owner_id != up.app_user_id;";
        //also filters out playlists they actually created
        //so the playlists returned are ones that they're ONLY a collaborator on, NOT the creator

        List<Playlist> playlists = jdbcTemplate.query(sql, new PlaylistMapper(), appUserId);
        playlists.forEach(p -> addPlaylistCreator(p));

        return playlists;
    }

    //////////////////////////////////////////////////////////////////
    //Playlist methods: findById(), add, and delete
    //////////////////////////////////////////////////////////////////

    @Override
    @Transactional
    public Playlist findById(int playlistId) {

        final String sql = "select playlist_id, `name`, `description`, owner_id from playlist where playlist_id = ?;";

        Playlist playlist = jdbcTemplate.query(sql, new PlaylistMapper(), playlistId).stream()
                .findFirst().orElse(null);

        if (playlist != null) {
//            addImage(playlist);
//            addTags(playlist);
            addTracks(playlist);
//            addCollaborators(playlist);
            addPlaylistCreator(playlist);
        }

        return playlist;
    }

    @Override
    public Playlist add(Playlist playlist) {

        final String sql = "insert into playlist (`name`, `description`, app_user_id) values ( ?, ?, ?);";

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
    @Transactional
    public boolean deleteById(int playlistId) {
        jdbcTemplate.update("delete from user_playlist where playlist_id = ?;", playlistId);
        jdbcTemplate.update("delete from tag where playlist_id = ?;", playlistId);
        jdbcTemplate.update("delete from image where playlist_id = ?;", playlistId);
        jdbcTemplate.update("delete from track where playlist_id = ?;", playlistId);
        return jdbcTemplate.update("delete from playlist where playlist_id = ?;", playlistId) > 0;
    }

    @Override
    public Tag findByContent(String tag) {
        return null;
    }

    //////////////////////////////////////////////////////////////////
    //everything Tag related
    //////////////////////////////////////////////////////////////////

    @Override
    public Tag addTagToDatabase(String tag, int appUserId) {
        Tag addedTag = new Tag();

        final String sql = "insert into tag (content, app_user_id) values (?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag);
            ps.setInt(2, appUserId);
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        addedTag.setTagId(keyHolder.getKey().intValue());
        addedTag.setContent(tag);
        addedTag.setAppUserId(appUserId);

        return addedTag;
    }

    @Override
    public boolean deleteTag(int tagId) {
        return jdbcTemplate.update("delete from tag where tag_id = ?;", tagId) > 0;
    }

    private void addTags(Playlist playlist) {

        final String sql = "select t.tag_id, t.content, t.app_user_id, t.playlist_id "
                + "from tag t "
                + "inner join playlist p on t.playlist_id = p.playlist_id "
                + "where t.playlist_id = ?;";

        List<Tag> tags = jdbcTemplate.query(sql, new TagMapper(), playlist.getPlaylistId());
        playlist.setTags(tags);
    }

    //////////////////////////////////////////////////////////////////
    //everything Image related
    //////////////////////////////////////////////////////////////////

//    @Override
//    public Image addImageToDatabase(Image image) {
//
//        final String sql = "insert into image (url, height, width, playlist_id) values (?, ?, ?, ?);";
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//            int rowsAffected = jdbcTemplate.update(connection -> {
//                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//                ps.setString(1, image.getUrl());
//                ps.setInt(2, image.getHeight());
//                ps.setInt(3, image.getWidth());
//                ps.setInt(4, image.getPlaylistId());
//                return ps;
//        }, keyHolder);
//
//        if (rowsAffected <= 0) {
//            return null;
//        }
//
//        image.setImageId(keyHolder.getKey().intValue());
//
//        return image;
//    }
//
//    private void addImage(Playlist playlist) {
//
//        final String sql = "select image_id, url, height, width, playlist_id from image where playlist_id = ?;";
//
//        Image image = jdbcTemplate.query(sql, new ImageMapper(), playlist.getPlaylistId()).stream()
//                .findFirst().orElse(null);
//
//        playlist.setImage(image);
//    }
//
//    //might not need this since image will get deleted if the playlist is deleted
////    @Override
////    @Transactional
////    public boolean deleteImage(int imageId) {
////        return jdbcTemplate.update("delete from image where image_id = ?;", imageId) > 0;
////    }

    //////////////////////////////////////////////////////////////////
    //everything Track related
    //////////////////////////////////////////////////////////////////

    @Override
    public Track addTrackToDatabase(Track track) {

        final String sql = "insert into track (`name`, duration_ms, artist, app_user_id, playlist_id) values "
                + "(?, ?, ?, ?, ?),";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, track.getName());
            ps.setLong(2, track.getDuration());
            ps.setString(3, track.getArtist());
//            ps.setInt(4, track.getAppUserId());
//            ps.setInt(5, track.getPlaylistId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        return track;
    }

    @Override
    public boolean deleteTrack(int trackId) {
        return jdbcTemplate.update("delete from track where track_id = ?;", trackId) > 0;
    }

    //@Override
    //    public List<PlaylistTrack> findAllByPlaylist(Playlist playlist) {
    //
    //        final String sql = "select track_id, `name`, duration_ms, artist, app_user_id, playlist_id from track where playlist_id = ?;";
    //
    //        return jdbcTemplate.query(sql, new PlaylistTrackMapper(), playlist.getPlaylistId());
    //    }

    private void addTracks(Playlist playlist) {

        final String sql = "select t.track_id, t.`name`, t.duration_ms, t.artist "
                + "from track t "
                + "inner join track_playlist tp on t.track_id = tp.track_id "
                + "inner join playlist p on tp.playlist_id = p.playlist_id "
                + "where tp.playlist_id = ?;";

        List<Track> tracks = jdbcTemplate.query(sql, new TrackMapper(), playlist.getPlaylistId());

        playlist.setTracks(tracks);
    }

    //////////////////////////////////////////////////////////////////
    //adding collaborators
    //////////////////////////////////////////////////////////////////

    private void addCollaborators(Playlist playlist) {

        final String sql = "select up.app_user_id, up.playlist_id, up.accepted "
                + "from user_playlist up "
                + "inner join playlist p on up.playlist_id = p.playlist_id "
                + "inner join app_user au on up.app_user_id = au.app_user_id "
                + "where p.playlist_id = ?;";

        List<Collaborator> collaborators = jdbcTemplate.query(sql, new CollaboratorMapper(), playlist.getPlaylistId());
        playlist.setCollaborators(collaborators);

        //TODO: figure out how to update user_playlist table when a user becomes a new collaborator of a playlist
        //right now we can only select who's in the database but we don't know how to add to the database
        //figure out what's being returned
    }

    private void addPlaylistCreator(Playlist playlist) {

        final String sqlForRoles = "select ar.`name` "
                + "from user_role ur "
                + "inner join app_role ar on ur.app_role_id = ar.app_role_id "
                + "where ur.app_user_id = ?;";

        List<String> roles = jdbcTemplate.query(sqlForRoles, new RoleMapper(), playlist.getAppUserId());

        final String sql = "select au.app_user_id, au.first_name, au.last_name, au.username, au.password_hash, au.email, au.disabled "
                + "from app_user au "
                + "inner join playlist p on au.app_user_id = p.owner_id "
                + "where p.playlist_id = ?;";

        AppUser playlistCreator = jdbcTemplate.query(sql, new AppUserMapper(roles), playlist.getPlaylistId()).stream()
                .findFirst().orElse(null);
        playlistCreator.setRoles(roles);
        playlist.setAppUser(playlistCreator);
    }

}
