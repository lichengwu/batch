package oliver.app.batch.service;

import java.io.IOException;

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

    /**
     * 天津首创爱这城最新消息
     */
    public void getMyHousingInfo() {

        try {
            Document document = Jsoup.connect("http://aizhechengsc.soufun.com/").get();
            Elements elements = document.select("#newtjxq_B02_06");
            // 房价
            String price = elements.select("span.arial20_red").first().ownText();
            // 房价走势
            String priceTendencyLink = elements.select("a:contains(房价走势)").first().attr("href");

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
            System.out.println(priceTendencyLink);
        } catch (IOException e) {
        }

    }

}
