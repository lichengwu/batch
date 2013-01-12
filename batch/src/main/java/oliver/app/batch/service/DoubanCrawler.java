package oliver.app.batch.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import oliver.app.batch.web.util.WebCrawlUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * DoubanCrawler haha
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2013-01-12 PM11:13
 */
public class DoubanCrawler extends WebCrawler {

    private static final Logger log = LoggerFactory.getLogger(DoubanCrawler.class);

    private DoubanCrawler() {
        super();
    }

    public static DoubanCrawler newInstance() {
        return new DoubanCrawler();
    }

    /**
     * Process the given url, get urls from html
     * 
     * @param url
     * 
     * @return
     */
    @Override
    protected List<URL> processUrl(URL url) {

        List<URL> linkInPage = new ArrayList<URL>();

        WebCrawlUtil webCrawlUtil = WebCrawlUtil.newInstance();
        String html = webCrawlUtil.getHTML(url.toString());

        if (StringUtils.hasLength(html)) {
            Document document = Jsoup.parse(html);

            System.out.println(document.text());

            Elements links = document.select("a[href]");

            for (Element link : links) {
                String rawLink = link.attr("abs:href");
                try {
                    if (StringUtils.hasLength(rawLink) && !rawLink.startsWith("#")) {
                        linkInPage.add(new URL(rawLink));
                    }

                } catch (MalformedURLException e) {
                    log.info("ignore url :" + rawLink);
                }
            }
        }

        return linkInPage;
    }

    /**
     * custom max thread
     * 
     * @return
     */
    @Override
    protected int getMaxThreadNum() {
        return 5;
    }

    /**
     * custom min thread
     * 
     * @return
     */
    @Override
    protected int getMinThreadNum() {
        return -1;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        DoubanCrawler crawler = DoubanCrawler.newInstance();

        List<URL> taskList = new ArrayList<URL>();
        taskList.add(new URL("http://www.douban.com/"));

        crawler.setTask(taskList);

        crawler.start();

        TimeUnit.SECONDS.sleep(100);

        crawler.stop();

        // Document document = Jsoup.connect("http://www.douban.com").get();
        // Elements links = document.select("a[href]");
        //
        // for (Element link : links) {
        // System.out.println(link.attr("abs:href"));
        // }

    }
}
