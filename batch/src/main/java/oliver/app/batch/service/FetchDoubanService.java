package oliver.app.batch.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import oliver.app.batch.bean.HtmlBean;
import oliver.app.batch.web.util.WebCrawlUtil;

import org.springframework.stereotype.Service;

/**
 * fetch info from http://www.douban.com/
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2013-01-03 4:17 PM
 */
@Service
public class FetchDoubanService {

    BlockingQueue<HtmlBean> queue = new ArrayBlockingQueue<HtmlBean>(20, true);

    WebCrawlUtil webCrawlUtil = null;

    @Resource
    ProxyService proxyService;

    /**
     * init method for setting
     */
    @PostConstruct
    protected void init() {
        webCrawlUtil = WebCrawlUtil.newProxyInstance(proxyService.getTopN(100));
        webCrawlUtil.setTimeout(5000);
    }

    private void fetchContect(String url) {

    }

}
