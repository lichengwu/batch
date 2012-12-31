package oliver.app.batch.service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Iterator;

import oliver.app.batch.domain.Proxy;
import oliver.app.batch.persistence.ProxyMapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ProxyMapper proxyMapper;

    public void fetch1() {

        for (int i = 1; i <= 1; i++) {
            String baseUrl = "http://www.cnproxy.com/proxy{0}.html";
            String url = MessageFormat.format(baseUrl, i);
            try {
                Document document = Jsoup.connect(url).get();
                System.out.println(document.body());
                Elements tr = document.select("#proxylisttb").last().select("tr");
                Iterator<Element> iterator = tr.iterator();
                while (iterator.hasNext()) {
                    System.out.println(iterator.next().html());
                    Elements td = iterator.next().select("td");
                    // System.out.println(td.get(0));
                }
                System.out.println(tr.html());
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }

        }

    }

    @Transactional
    public void save(){
        Proxy proxy = new Proxy();

        proxy.setIp("127.0.0.1");
        proxy.setPort(8087);
        proxy.setStatus(1);
        proxy.setUpdateTime(new Date());
        proxy.setAddTime(new Date());

        proxyMapper.insert(proxy);
    }

}
