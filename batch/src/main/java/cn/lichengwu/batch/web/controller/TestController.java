package cn.lichengwu.batch.web.controller;

import java.util.HashMap;

import javax.annotation.Resource;

import cn.lichengwu.batch.dao.SimpleJbdcTest;
import cn.lichengwu.batch.service.MailService;
import cn.lichengwu.batch.service.SubscriptionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * controller for test
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-24 PM4:47
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Resource
    SimpleJbdcTest test;

    @Resource
    MailService mailService;

    @Resource
    SubscriptionService subscriptionService;

    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("/test/index");
        mv.addObject("data", "sss");
        subscriptionService.notifyMyHousingInfo();
        return mv;
    }

    /**
     * test for mail service
     * 
     * @author lichengwu
     * @created 2012-12-26
     * 
     * @return
     */
    @RequestMapping("/mail")
    @ResponseBody
    public String testMail() {
        try {
            mailService.send("test", new HashMap<String, Object>());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return e.getMessage();
        } finally {
        }
        return "mail send";
    }
}
