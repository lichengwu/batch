package cn.lichengwu.app.batch.dao;

import org.springframework.stereotype.Repository;

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
