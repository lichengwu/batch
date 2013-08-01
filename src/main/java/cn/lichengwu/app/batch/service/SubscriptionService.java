package cn.lichengwu.app.batch.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * subscription
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-24 PM7:06
 */
@Service
public class SubscriptionService {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionService.class);

    @Resource
    MailService mailService;

    @Resource
    JdbcTemplate jdbcTemplate;

    /**
     * 天津首创爱这城最新消息
     * <p>
     * 每天06点和18点执行一次
     * </p>
     * 
     * @author lichengwu
     * @created 2012-12-24
     */
    @Transactional
    @Scheduled(cron = "0 0 6/12 * * ?")
    public void notifyMyHousingInfo() {

        log.info("start gathering housing info...");

        final String RECORD_KEY = "housing_info";

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

            String sql = "select new_record from record where record_key=? order by add_time desc limit 1";

            String lastPrice = null;

            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, new Object[] { RECORD_KEY });

            if (rowSet.next()) {
                lastPrice = rowSet.getString(1);
            }

            if (price != null && !price.equals(lastPrice)) {

                log.info("price change : " + lastPrice + " -> " + price);

                String insert_sql = "INSERT INTO record (record_key,old_record,new_record,add_time) VALUES (?,?,?,?)";

                jdbcTemplate.update(insert_sql, RECORD_KEY, lastPrice, price, new Date());
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("newPrice", price);
                data.put("oldPrice", lastPrice);
                data.put("link", priceTendencyLink);

                mailService.send("housingInfo", data);
            } else {
                log.info("same price no change!");
            }

            log.info("gather housing info finished...");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }

}
