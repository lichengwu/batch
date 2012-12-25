package oliver.app.batch.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

/**
 * subscription
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-24 PM7:06
 */
@Service
public class SubscriptionService {

    @Resource
    MailService mailService;

    /**
     * 天津首创爱这城最新消息
     */
    public void notifyMyHousingInfo() {

        try {
            Document document = Jsoup.connect("http://aizhechengsc.soufun.com/").get();
            Elements elements = document.select("#newtjxq_B02_06");
            // 房价
            String price = elements.select("span.arial20_red").first().ownText();
            // 房价走势
            String priceTendencyLink = elements.select("a:contains(房价走势)").first().attr("href");

            // document = Jsoup.connect(priceTendencyLink).get();

            // String priceTendencyDataLink =
            // document.select("#zsli022").select("iframe")
            // .attr("abs:src");

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("newPrice", price);
            data.put("link", priceTendencyLink);

            mailService.send("housingInfo", data);

        } catch (IOException e) {
        }

    }

    public static void main(String[] args) {
        try {
            Document document = Jsoup.connect("http://aizhechengsc.soufun.com/").get();
            Elements elements = document.select("#newtjxq_B02_06");
            // 房价
            String price = elements.select("span.arial20_red").first().ownText();
            // 房价走势
            String priceTendencyLink = elements.select("a:contains(房价走势)").first().attr("href");

            document = Jsoup.connect(priceTendencyLink).get();

            String priceTendencyDataLink = document.select("#zsli022").select("iframe")
                    .attr("abs:src");

            String html = Jsoup.connect(priceTendencyDataLink).get().html();

        } catch (IOException e) {
        }

    }

}
