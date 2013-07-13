package cn.lichengwu.batch.web.util;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.List;

import javax.net.ssl.SSLException;

import cn.lichengwu.batch.domain.Proxy;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
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

    private static final HttpRequestRetryHandler RETRY_HANDLER = new HttpRequestRetryHandler() {

        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            if (executionCount >= 3) {
                // Do not retry if over max retry count
                return false;
            }
            if (exception instanceof InterruptedIOException) {
                // Timeout
                return true;
            }
            if (exception instanceof UnknownHostException) {
                // Unknown host
                return false;
            }
            if (exception instanceof ConnectException) {
                // Connection refused
                return true;
            }
            if (exception instanceof SSLException) {
                // SSL handshake exception
                return false;
            }
            HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
            boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
            if (idempotent) {
                // Retry if the request is considered idempotent
                return true;
            }
            return true;
        }

    };

    private final int DEFAULT_TIMEOUT = 3000;

    private static final Logger log = LoggerFactory.getLogger(WebCrawlUtil.class);

    private List<Proxy> proxies;

    private int timeout;

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
        DefaultHttpClient client = new DefaultHttpClient();
        client.setHttpRequestRetryHandler(RETRY_HANDLER);

        // set proxy
        if (proxies != null) {
            Proxy proxy = proxies.get((int) Math.random() * proxies.size() % proxies.size());
            HttpHost host = new HttpHost(proxy.getIp(), proxy.getPort());
            client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, host);
        }

        HttpGet get = new HttpGet(url);
        // set timeout
        HttpConnectionParams.setConnectionTimeout(get.getParams(), getTimeout());
        // set socket timeout
        HttpConnectionParams.setSoTimeout(get.getParams(), getTimeout());

        get.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 5.1; rv:5.0) Gecko/20100101 Firefox/5.0");
        get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        get.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
        get.setHeader("Accept-Encoding", "GB2312,utf-8;q=0.7,*;q=0.7");
        get.setHeader("Referrer", "http://movie.douban.com/");
        get.setHeader("Cache-Control", "max-age=0");

        try {
            HttpResponse response = client.execute(get);

            if (response.getStatusLine().getStatusCode() == 200) {
                html = EntityUtils.toString(response.getEntity(),"utf-8");
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
    public int getTimeout() {
        return timeout;
    }

    /**
     * set url connection timeout
     * 
     * @param timeout
     */
    public void setTimeout(int timeout) {
        if (timeout == 0) {
            timeout = DEFAULT_TIMEOUT;
        }
        this.timeout = timeout;
    }

    /**
     * test whether the proxy is available
     * 
     * @param proxy
     *            proxy
     * @return
     */
    public static boolean testProxy(Proxy proxy, Integer timeout) {

        DefaultHttpClient client = new DefaultHttpClient();
        client.setHttpRequestRetryHandler(RETRY_HANDLER);
        // set proxy
        HttpHost host = new HttpHost(proxy.getIp(), proxy.getPort());
        client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, host);

        HttpGet get = new HttpGet("http://www.baidu.com");
        // set timeout
        HttpConnectionParams.setConnectionTimeout(get.getParams(), timeout);
        // set socket timeout
        HttpConnectionParams.setSoTimeout(get.getParams(), timeout);
        try {
            HttpResponse response = client.execute(get);

            if (response.getStatusLine().getStatusCode() == 200) {
                return true;
            }

        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        } finally {
            get.releaseConnection();
        }
        return false;
    }

}
