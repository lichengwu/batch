package oliver.app.batch.web.controller;

import java.util.List;

import javax.annotation.Resource;

import oliver.app.batch.Configuration;
import oliver.app.batch.domain.Config;
import oliver.app.batch.service.SystemService;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * system config controller
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2013-01-03 2:15 PM
 */
@Controller
@RequestMapping("/sys")
public class SystemController {

    @Resource
    SystemService systemService;

    @Resource
    JdbcTemplate jdbcTemplate;

    /**
     * list all config
     * 
     * @return
     */
    @RequestMapping("/config/list")
    public ModelAndView listConfig() {
        ModelAndView mv = new ModelAndView("/sys/config_list");
        List<Config> configList = systemService.getAllConig();
        mv.addObject("configs", configList);
        return mv;
    }

    /**
     * reload {@link Configuration}
     * 
     * @return
     */
    @RequestMapping("/config/reload")
    @ResponseBody
    public Object reloadConfig() {
        Configuration.reload(jdbcTemplate.getDataSource());
        List<Config> configList = systemService.getAllConig();
        for (Config config : configList) {
            if (config.getName().contains("password")) {
                config.setValue("******");
            }
        }
        return configList;
    }
}
