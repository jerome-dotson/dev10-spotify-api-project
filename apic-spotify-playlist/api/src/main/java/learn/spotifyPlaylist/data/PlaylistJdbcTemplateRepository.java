package learn.spotifyPlaylist.data;

import learn.spotifyPlaylist.data.mappers.*;

import learn.spotifyPlaylist.models.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
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
            addCollaborators(playlist);
            addTracks(playlist);
            addPlaylistCreator(playlist);
        }

        return playlist;
    }

    @Override
    public Playlist add(Playlist playlist) {

        final String sql = "insert into playlist (`name`, `description`, owner_id) values (?, ?, ?);";

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
    public Playlist clonePlaylist(Playlist playlist) {

        final String sql = "insert into playlist (`name`, `description`, owner_id) values (?, ?, ?);";

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

        //now the cloned playlist is in the database,
        //but now the tracks have to be copied over into the track_playlist table

        //first we grab the list of tracks from the original playlist
        //and insert new rows of the same tracks into the track table
        //we do this because the new rows of the same tracks will still need their own Ids

        playlist.getTracks().forEach(
                t -> addTrackToDatabase(t, playlist.getPlaylistId(), playlist.getAppUserId()));

        //now that track and track_playlist are updated, we set the tracks to the clone playlist
        addTracks(playlist);

        return playlist;
    }

    @Override
    @Transactional
    public boolean deleteById(int playlistId) {
        jdbcTemplate.update("delete from track_playlist where playlist_id = ?;", playlistId);
        jdbcTemplate.update("delete from collaborator where playlist_id = ?;", playlistId);
        jdbcTemplate.update("delete from tag_playlist where playlist_id = ?;", playlistId);
        return jdbcTemplate.update("delete from playlist where playlist_id = ?;", playlistId) > 0;
    }

    //////////////////////////////////////////////////////////////////
    //everything Tag related
    //////////////////////////////////////////////////////////////////

    @Override
    public Tag findByContent(String tag) {
        return null;
    }

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
    public Track addTrackToDatabase(Track track, int playlistId, int appUserId) {

        //inserting track into track table
        final String sql = "insert into track (`name`, duration_ms, artist) values (?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, track.getName());
            ps.setLong(2, track.getDuration());
            ps.setString(3, track.getArtist());

            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        track.setTrackId(keyHolder.getKey().intValue());

        //then we update the track_playlist table to associate it with a playlist
        final String sqlTP = "insert into track_playlist (track_id, playlist_id, app_user_id) "
                + "values (?, ?, ?);";

        boolean updateTrackPlaylist = jdbcTemplate.update(sqlTP, track.getTrackId(), playlistId, appUserId) > 0;
        if (!updateTrackPlaylist) {
            return null;
        }

        return track;
    }

    @Override
    @Transactional
    public boolean deleteTrack(int trackId) {
        jdbcTemplate.update("delete from track_playlist where track_id = ?;", trackId);
        return jdbcTemplate.update("delete from track where track_id = ?;", trackId) > 0;
    }

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
    //adding owner and collaborators
    //////////////////////////////////////////////////////////////////

    @Override
    public List<AppUser> findCollaborators(int appUserId) {

        List<String> placeHolder = new ArrayList<>(); //just to satisfy roles parameter

        final String sql = "select * from app_user;";

        return jdbcTemplate.query(sql, new AppUserMapper(placeHolder))
                .stream().filter(u -> u.getAppUserId() != appUserId).toList();
    }

    @Override
    public List<AppUser> playlistCollaborators(int playlistId) {

        Playlist playlist = findById(playlistId);

        return playlist.getCollaborators();
    }

    @Override
    public boolean deleteCollaborator(int playlistId, int appUserId) {

        final String sql = "delete from collaborator where playlist_id = ? and app_user_id = ?;";

        return jdbcTemplate.update(sql, playlistId, appUserId) > 0;
    }

    private void addCollaborators(Playlist playlist) {

        List<String> placeHolder = new ArrayList<>(); //just to satisfy roles parameter

        final String sql = "select au.app_user_id, au.first_name, au.last_name, au.username, au.password_hash, au.email, au.disabled "
                + "from app_user au "
                + "inner join collaborator c on au.app_user_id = c.app_user_id "
                + "inner join playlist p on c.playlist_id = p.playlist_id "
                + "where p.playlist_id = ? and c.accepted = 1;"; //only returns users where accepted = true

        List<AppUser> collaborators = jdbcTemplate.query(sql, new AppUserMapper(placeHolder), playlist.getPlaylistId());
        //list of roles is set to null per user just to load appUser info, roles data prob not important per collaborator here
        playlist.setCollaborators(collaborators);
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

    //////////////////////////////////////////////////////////////////
    // playlist invites
    //////////////////////////////////////////////////////////////////

    //TODO: figure out how to update user_playlist table when a user is sent an invite to a playlist
    //the row data will always start with accepted = 0 (false) -> add method
    //if the user declines the invite, then the invite will be deleted -> deleted method

    @Override
    public boolean sendInvite(Collaborator collaborator) {
        final String sql = "insert into collaborator (app_user_id, playlist_id, accepted) values (?, ?, 0);";
        return jdbcTemplate.update(sql, collaborator.getAppUserId(), collaborator.getPlaylistId()) > 0;
    }


    @Override
    public boolean acceptInvite(Collaborator collaborator) {

        final String sql = " update collaborator set accepted = 1 where app_user_id = ? and playlist_id = ?;";

        return jdbcTemplate.update(sql, collaborator.getAppUserId(), collaborator.getPlaylistId()) > 0;
    }

    @Override
    @Transactional
    public boolean denyInvite(int playlistId, int appUserId) {

        final String sql = "delete from collaborator where playlist_id = ? and app_user_id = ? and accepted = 0;";

        return jdbcTemplate.update(sql, playlistId, appUserId) > 0;
    }

    @Override
    public List<Playlist> searchPlaylistsByName(String playlistName) {
            final String sql = "select playlist_id, `name`, `description`, owner_id from playlist where `name` like ?;";

            return jdbcTemplate.query(sql, new PlaylistMapper(), "%" + playlistName + "%");
        }



}
