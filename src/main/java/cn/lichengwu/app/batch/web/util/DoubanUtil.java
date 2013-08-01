package cn.lichengwu.app.batch.web.util;

import java.util.Date;

import cn.lichengwu.app.batch.core.DomainParser;
import cn.lichengwu.app.batch.core.Matchable;
import cn.lichengwu.app.batch.domain.douban.Movie;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * util for parse douban.com
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2013-01-03 5:30 PM
 */
public final class DoubanUtil {

    /**
     * get type from url
     * 
     * @param url
     * @return
     */
    public static DoubanType getType(String url) {
        for (DoubanType type : DoubanType.values()) {
            if (type.match(url)) {
                return type;
            }
        }

        return null;
    }

    public enum DoubanType implements Matchable, DomainParser<Object> {
        /**
         * movie
         */
        MOVIE() {
            @Override
            public boolean match(Object value) {
                if (value == null) {
                    return false;
                }
                String url = (String) value;
                return url.matches("http://movie.douban.com/subject/[0-9]+/");
            }

            @Override
            public Movie parse(Document document) {
                Elements elements = document.select("#wrapper");

                Date now = new Date();
                Movie movie = new Movie();
                movie.setAddTime(now);
                movie.setModTime(now);
                movie.setMovieName(elements.select("h1 > span").first().text());

                return null;
            }
        },
        /**
         * book
         */
        BOOK() {
            @Override
            public boolean match(Object value) {
                if (value == null) {
                    return false;
                }
                String url = (String) value;
                return url.matches("http://book.douban.com/subject/[0-9]+/");
            }

            @Override
            public Object parse(Document document) {
                return null;
            }
        },
        /**
         * music
         */
        MUSIC() {
            @Override
            public boolean match(Object value) {
                if (value == null) {
                    return false;
                }
                String url = (String) value;
                return url.matches("http://music.douban.com/subject/[0-9]+/");
            }

            @Override
            public Object parse(Document document) {
                return null;
            }
        };

    }
}
