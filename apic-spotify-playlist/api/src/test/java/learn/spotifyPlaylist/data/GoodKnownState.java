package learn.spotifyPlaylist.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GoodKnownState {

    @Autowired
    JdbcTemplate jdbcTemplate;

    static boolean hasRun = false;

    @Transactional
    void set() {
        if (!hasRun) {
            jdbcTemplate.update("call set_good_known_state();");
            hasRun = true;
        }
    }
}
