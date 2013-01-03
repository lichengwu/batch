package oliver.app.batch.service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import oliver.app.batch.constant.ProxyStatus;
import oliver.app.batch.constant.ProxyType;
import oliver.app.batch.domain.Proxy;
import oliver.app.batch.persistence.ProxyMapper;
import oliver.app.batch.util.StringUtil;
import oliver.app.batch.web.util.WebCrawlUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * fetch proxy from internet
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-30 9:25 PM
 */
@Service
public class ProxyService {

    private static final Logger log = LoggerFactory.getLogger(ProxyService.class);

    private final ExecutorService exec = Executors.newFixedThreadPool(5);

    @Resource
    private ProxyMapper proxyMapper;

    /**
     * fetch proxy from http://www.cnproxy.com
     */
    @Transactional
    @Scheduled(cron = "0 0 12 * * ?")
    public void fetchProxy() {

        log.info("start fetch proxy from http://www.cnproxy.com ...");

        int iCount = 0;

        for (int i = 1; i <= 10; i++) {
            Map<String, String> portMap = new HashMap<String, String>();
            String baseUrl = "http://www.cnproxy.com/proxy{0}.html";
            String url = MessageFormat.format(baseUrl, i);
            try {
                Document document = Jsoup.connect(url).get();
                // parse port info
                try {
                    Elements script = document.head().select("script");
                    String[] mappingInfo = script.first().html().split(";");
                    for (String info : mappingInfo) {
                        String[] portInfo = info.split("=");
                        portMap.put(portInfo[0], portInfo[1].substring(1, 2));
                    }
                } catch (Throwable e) {
                    log.error(e.getMessage(), e);
                }
                Elements tr = document.select("#proxylisttb").last().select("tr");
                Iterator<Element> iterator = tr.iterator();
                while (iterator.hasNext()) {
                    Elements td = iterator.next().select("td");
                    String ip = td.first().text();
                    if (StringUtil.isIp(ip)) {

                        Proxy existsProxy = proxyMapper.findByIp(ip);
                        if (existsProxy != null) {
                            log.info("[exists]-->" + existsProxy.toString());
                            break;
                        }

                        String portStr = td.first().select("script").html().substring(19);
                        portStr = portStr.substring(0, portStr.length() - 1);
                        String[] split = portStr.split("\\+");
                        StringBuilder port = new StringBuilder();
                        for (String number : split) {
                            port.append(portMap.get(number));
                        }

                        ProxyType proxyType = ProxyType.getProxyType(td.get(1).text());
                        if (proxyType == null) {
                            proxyType = ProxyType.UNKNOWN;
                        }

                        Date now = new Date();
                        Proxy proxy = new Proxy();
                        proxy.setIp(ip);
                        proxy.setPort(Integer.valueOf(port.toString()));
                        proxy.setAddTime(now);
                        proxy.setStatus(ProxyStatus.OK.getIndex());
                        proxy.setType(proxyType.getIndex());
                        proxy.setNote(td.get(3).text());
                        proxyMapper.insert(proxy);
                        iCount++;
                    }

                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }

        }

        log.info("[{}] new proxies added!", iCount);
        log.info("finish fetch proxy from http://www.cnproxy.com ...");

    }

    /**
     * test all proxies
     */
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void testProxies() {

        List<Proxy> proxyList = proxyMapper.findAll();

        for (final Proxy proxy : proxyList) {

            exec.submit(new Runnable() {
                @Override
                public void run() {
                    long begin = System.currentTimeMillis();
                    boolean available = WebCrawlUtil.testProxy(proxy, 5000);
                    Long responseTime = System.currentTimeMillis() - begin;
                    if (available && proxy.getStatus() == ProxyStatus.EXPIRED.getIndex()) {
                        proxy.setStatus(ProxyStatus.RECOVERED.getIndex());
                        proxy.setResponseTime(responseTime.intValue());
                        log.info(proxy.toString() + " is RECOVERED.");
                    } else if (!available
                            && (proxy.getStatus() == ProxyStatus.OK.getIndex() || proxy.getStatus() == ProxyStatus.RECOVERED
                                    .getIndex())) {
                        proxy.setStatus(ProxyStatus.EXPIRED.getIndex());
                        proxy.setResponseTime(responseTime.intValue());
                        log.info(proxy.toString() + " is EXPIRED.");
                    } else {
                        log.info(proxy.toString() + " is OK.");
                    }
                    proxy.setUpdateTime(new Date());
                    proxyMapper.update(proxy);
                }
            });

        }

    }

    @Test
    public void fetch2() {
        String baseUrl = "http://www.freeproxylists.net/zh/?page={0}";

        for (int i = 1; i <= 1; i++) {
            String url = MessageFormat.format(baseUrl, i);
            try {
                Document document = Jsoup.connect(url).get();
                Elements elements = document.select("table.DataGrid");
                System.out.println(elements.html());
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }

        }

    }

    @Transactional
    public void save() {
        Proxy proxy = new Proxy();

        proxy.setIp("127.0.0.1");
        proxy.setPort(8087);
        proxy.setStatus(1);
        proxy.setUpdateTime(new Date());
        proxy.setAddTime(new Date());

        proxyMapper.insert(proxy);
    }

    /**
     * get top N {@link Proxy} by response time
     * 
     * @param top
     * @return
     */
    public List<Proxy> getTopN(int top) {
        return proxyMapper.getTopProxy(top);
    }

    /**
     * destroy bean
     */
    @PreDestroy
    public final void destroy() {
        // show down exec
        exec.shutdown();
    }

}
