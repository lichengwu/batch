package cn.lichengwu.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * configuration for project, load project properties from database by default
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-25 AM10:48
 */
public final class Configuration {

    private static final Logger log = LoggerFactory.getLogger(Configuration.class);

    private static final Properties PROPERTIES = new Properties();

    /**
     * reload configuration
     * 
     * @author lichengwu
     * @created 2012-12-25
     * 
     * @return whether reload is successful
     */
    public static synchronized boolean reload(DataSource dataSource) {
        try {
            PROPERTIES.clear();
            loadFromDB(dataSource);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * load properties from database
     * 
     * @author lichengwu
     * @created 2012-12-25
     * 
     * @param dataSource
     */
    private static void loadFromDB(DataSource dataSource) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        Map<String, String> data = template.query("select namespace,name,value from config",
                new ResultSetExtractor<Map<String, String>>() {
                    @Override
                    public Map<String, String> extractData(ResultSet rs) throws SQLException,
                            DataAccessException {
                        Map<String, String> data = new HashMap<String, String>();
                        while (rs.next()) {
                            data.put(rs.getString(1) + "." + rs.getString(2), rs.getString(3));
                        }
                        return data;
                    }
                });
        PROPERTIES.putAll(data);
        PROPERTIES.list(System.out);
    }

    /**
     * get property by then given key
     * 
     * @author lichengwu
     * @created 2012-12-25
     * 
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    /**
     * get {@link Integer} property by then given key
     * 
     * @author lichengwu
     * @created 2012-12-25
     * @param key
     * @return
     */
    public static Integer getProperty2Integer(String key) {
        String property = PROPERTIES.getProperty(key);
        if (property != null) {
            return Integer.valueOf(key);
        }
        return null;
    }

    /**
     * get all properties in {@link Configuration}
     * 
     * @return
     */
    public static Properties getProperties() {
        Properties props = new Properties();
        for (String key : PROPERTIES.stringPropertyNames()) {
            props.put(key, PROPERTIES.get(key));
        }
        return props;
    }

}
