package cn.lichengwu.app.batch;

import java.io.FileNotFoundException;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

/**
 * BaseTest for extends
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-25 PM9:43
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext-*.xml")
public class BaseTest {

    @Resource
    JdbcTemplate jdbcTemplate;

    @BeforeClass
    public static void setUpBeforeClass() {

        try {
            Log4jConfigurer.initLogging("classpath:log4j.xml");
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    @Before
    public final void before() {
        // init configuration
        Configuration.reload(jdbcTemplate.getDataSource());
    }

    @Test
    public void noop() {
        // do nothing here
        // declare the void method to avoid maven build failure
    }
}
