package oliver.app.batch.web.util;

import java.io.IOException;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

/**
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2013-01-13 PM2:27
 */
public class DoubanMovieParserTest {

    @Test
    public void test() throws IOException {
        Document document = Jsoup.connect("http://movie.douban.com/subject/10540066/").get();

        Elements elements = document.select("#wrapper");

        Elements movieInfo = elements.select("#info");

        String url = document.baseUri();
        if (!url.endsWith("/")) {
            url += "/";
        }
        int movieId = Integer.valueOf(url.substring(url.indexOf("subject/") + 8, url.length() - 1));
        String movieName = elements.select("h1 > span").first().text();

        System.out.println();

        String[] split = movieInfo.text().split("\\s");
        System.out.println(Arrays.toString(split));

//        for (Element element : movieInfo) {
//            if (element.tagName().equalsIgnoreCase("br")) {
//               System.out.println();
//            } else {
//                System.out.print(element.text());
//            }
//
//        }

        System.out.println("movie id : " + movieId);
        System.out.println("movie name : " + movieName);

    }
}
