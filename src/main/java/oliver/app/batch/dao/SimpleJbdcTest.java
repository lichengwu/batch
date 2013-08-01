package oliver.app.batch.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-24 PM9:46
 */
@Repository
public class SimpleJbdcTest {

    // @Resource
    // DataSource dataSource;
    //
    // @Transactional
    // public List<Map> findAll() {
    // final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    // return jdbcTemplate.query("select * from current_states", new
    // RowMapper<Map>() {
    // public Map mapRow(ResultSet rs, int rowNum) throws SQLException {
    // Map s = new HashMap();
    // s.put("id", rs.getLong("id"));
    // s.put("code", rs.getString("state_code"));
    // s.put("name", rs.getString("name"));
    // return s;
    // }
    // });
    // }

}
