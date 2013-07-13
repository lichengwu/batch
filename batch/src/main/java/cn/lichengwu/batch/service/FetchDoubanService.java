package cn.lichengwu.batch.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import cn.lichengwu.batch.bean.HtmlBean;
import cn.lichengwu.batch.persistence.ProxyMapper;
import cn.lichengwu.batch.web.util.DoubanUtil;
import cn.lichengwu.batch.web.util.WebCrawlUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * fetch info from http://www.douban.com/
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2013-01-03 4:17 PM
 */
@Service
public class FetchDoubanService {

    private static final Logger log = LoggerFactory.getLogger(FetchDoubanService.class);

    BlockingQueue<HtmlBean> queue = new ArrayBlockingQueue<HtmlBean>(20, true);

    ExecutorService fetchExec = null;

    WebCrawlUtil webCrawlUtil = null;

    @Resource
    ProxyMapper proxyMapper;

    /**
     * init method for setting
     */
    @PostConstruct
    protected void init() {
        webCrawlUtil = WebCrawlUtil.newInstance();
        webCrawlUtil.setTimeout(5000);

        fetchExec = Executors.newFixedThreadPool(10);
    }

    public void fetchContent(String url) {
        fetchExec.execute(new FetchHtmlTask(url));
    }

    class FetchHtmlTask implements Runnable {

        private String url;

        FetchHtmlTask(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            String html = webCrawlUtil.getHTML(url);
            Document document = Jsoup.parse(html, url);
            if (DoubanUtil.getType(url) == DoubanUtil.DoubanType.MOVIE) {
                try {
                    queue.put(new HtmlBean(url, document));
                    log.debug("[{}],{}", queue.size(), url);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                    Thread.currentThread().interrupt();
                }

            }
            Elements links = document.select("a[href]");
            for (Element link : links) {
                String newUrl = link.attr("abs:href");
                if (StringUtils.hasLength(newUrl)) {
                    fetchExec.execute(new FetchHtmlTask(newUrl));
                }
            }

        }
    }

}
