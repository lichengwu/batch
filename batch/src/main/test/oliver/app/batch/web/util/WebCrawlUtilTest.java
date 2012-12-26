package oliver.app.batch.web.util;

import java.util.ArrayList;
import java.util.List;

import oliver.app.batch.domain.Proxy;

import org.junit.Test;

/**
 * test for {@link WebCrawlUtil}
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-27 AM12:08
 */
public class WebCrawlUtilTest {

    @Test
    public void test() {

        List<Proxy> proxies = new ArrayList<Proxy>();
        Proxy proxy = new Proxy();
        proxy.setPort(8087);
        proxy.setIp("127.0.0.1");
        proxies.add(proxy);

        WebCrawlUtil crawlUtil = WebCrawlUtil.newProxyInstance(proxies);
        System.out.println(crawlUtil.getHTML("http://www.douban.com"));

    }

}
