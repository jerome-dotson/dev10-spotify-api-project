//package learn.spotifyPlaylist.data;
//
//import learn.spotifyPlaylist.models.Tag;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class TagJdbcTemplateRepository implements TagRepository {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public  TagJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    public Tag add(Tag tag) {
//
//        final String sql = "insert into tag (tag_id, content, app_user_id) values (?, ?, ?);";
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//            int rowsAffected = jdbcTemplate.update()
//    }
//
//    @Override
//    public Tag deleteById(int tagId) {
//        return null;
//    }
//}
