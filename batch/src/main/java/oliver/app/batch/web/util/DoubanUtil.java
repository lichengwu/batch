package oliver.app.batch.web.util;

import oliver.app.batch.core.Matchable;

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

    public enum DoubanType implements Matchable {
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
        };

    }
}
