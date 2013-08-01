package cn.lichengwu.app.batch.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import cn.lichengwu.app.batch.web.util.DoubanUtil;
import cn.lichengwu.app.batch.web.util.WebCrawlUtil;

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

    private final BlockingQueue<URL> workQueue = new LinkedBlockingDeque<URL>(50);

    private final CopyOnWriteArraySet<URL> processedUrl = new CopyOnWriteArraySet<URL>();

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
    protected Collection<URL> processUrl(URL url) {

        log.debug("start process url :" + url.toString());

        Collection<URL> linkInPage = new ArrayList<URL>();

        WebCrawlUtil webCrawlUtil = WebCrawlUtil.newInstance();
        String html = webCrawlUtil.getHTML(url.toString());

        if (StringUtils.hasLength(html)) {
            Document document = Jsoup.parse(html);

            Elements links = document.select("a[href]");

            for (Element element : links) {
                String rawLink = element.attr("abs:href");
                try {
                    if (StringUtils.hasLength(rawLink) && rawLink.startsWith("http")) {
                        URL link = new URL(rawLink);
                        if (DoubanUtil.DoubanType.MOVIE.match(rawLink)) {
                            workQueue.put(link);
                        }
                        linkInPage.add(link);
                    }

                } catch (MalformedURLException e) {
                    log.info("ignore url :" + rawLink);
                } catch (InterruptedException e) {
                    log.error("error occur while process url :" + e.getMessage());
                }
            }
        }

        if (!linkInPage.isEmpty()) {
            linkInPage.removeAll(processedUrl);
            processedUrl.addAll(linkInPage);
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

    /**
     * Retrieves and removes the head url of this crawler, waiting if necessary
     * until an url becomes available.
     * 
     * @return url
     * @throws InterruptedException
     */
    public URL take() throws InterruptedException {
        return workQueue.take();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        final DoubanCrawler crawler = DoubanCrawler.newInstance();

        List<URL> taskList = new ArrayList<URL>();
        taskList.add(new URL("http://movie.douban.com/"));

        crawler.setTask(taskList);

        crawler.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.println(crawler.take().toString());
                    }
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                    Thread.currentThread().interrupt();
                }
            }
        }).start();

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
