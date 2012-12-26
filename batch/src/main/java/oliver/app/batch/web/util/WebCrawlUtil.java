package oliver.app.batch.web.util;

import java.io.IOException;
import java.util.List;

import oliver.app.batch.domain.Proxy;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * crawl web page util
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-26 PM10:16
 */
public final class WebCrawlUtil {

    private final long DEFAULT_TIMEOUT = 3;

    private static final Logger log = LoggerFactory.getLogger(WebCrawlUtil.class);

    private List<Proxy> proxies;

    private long timeout;

    private WebCrawlUtil() {
    }

    private WebCrawlUtil(List<Proxy> proxies) {
        this.proxies = proxies;
    }

    /**
     * get the default instance
     * 
     * @return
     */
    public static WebCrawlUtil newInstance() {
        return new WebCrawlUtil();
    }

    /**
     * get the instance with proxy
     * 
     * @param proxies
     * @return
     */
    public static WebCrawlUtil newProxyInstance(List<Proxy> proxies) {
        return new WebCrawlUtil(proxies);

    }

    /**
     * get html content from url
     * 
     * @param url
     * @return html content or null
     */
    public String getHTML(String url) {
        String html = null;
        HttpClient client = new DefaultHttpClient();
        if (proxies != null) {
            Proxy proxy = proxies.get((int) Math.random() * proxies.size() % proxies.size());
            HttpHost host = new HttpHost(proxy.getIp(), proxy.getPort());
            client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, host);
        }

        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = client.execute(get);

            if (response.getStatusLine().getStatusCode() == 200) {
                html = EntityUtils.toString(response.getEntity());
            }

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            get.releaseConnection();
        }
        return html;
    }

    /**
     * get url connection timeout
     * 
     * @return
     */
    public long getTimeout() {
        return timeout;
    }

    /**
     * set url connection timeout
     * 
     * @param timeout
     */
    public void setTimeout(long timeout) {
        if (timeout == 0) {
            timeout = DEFAULT_TIMEOUT;
        }
        this.timeout = timeout;
    }

}
