package oliver.app.batch.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("/test/index");
        mv.addObject("data", "data");
        return mv;
    }
}
