package cn.lichengwu.batch.web;

import cn.lichengwu.batch.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * custom setting after Ioc inited
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-25 PM4:12
 */
public class SystemInitializer implements ApplicationContextAware {

    private static Logger log = LoggerFactory.getLogger(SystemInitializer.class);

    /**
     * init method
     */
    public void init() {
        log.info("SystemInitializer init...");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // load configuration
        JdbcTemplate jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
        Configuration.reload(jdbcTemplate.getDataSource());
    }
}
