package cn.lichengwu.batch.util;

/**
 * cache data for each thread
 *
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-26 PM5:42
 */
public final class ThreadCache {

    private static final ThreadLocal<ThreadContext> THEATRICAL = new ThreadLocal<ThreadContext>() {
        @Override
        protected ThreadContext initialValue() {
            return new ThreadContext();
        }
    };

    /**
     * get thread start time
     *
     * @return thread start time or null
     * @author lichengwu
     * @created 2012-12-26
     */
    public static long getStartTime() {
        return THEATRICAL.get().startTime;
    }

    /**
     * set thread start time
     *
     * @param startTime
     * @author lichengwu
     * @created 2012-12-26
     */
    public static void setStartTime(long startTime) {
        THEATRICAL.get().startTime = startTime;
    }

    /**
     * get remote request ip
     *
     * @return emote request ip or null
     * @author lichengwu
     * @created 2012-12-26
     */
    public static String getIp() {
        return THEATRICAL.get().ip;
    }

    /**
     * set remote request ip
     *
     * @param ip
     * @author lichengwu
     * @created 2012-12-26
     */
    public static void setIp(String ip) {
        THEATRICAL.get().ip = ip;
    }

    /**
     * get request uri
     *
     * @return request uri or null
     * @author lichengwu
     * @created 2012-12-26
     */
    public static String getUri() {
        return THEATRICAL.get().uri;
    }

    /**
     * set request uri
     *
     * @param uri
     * @author lichengwu
     * @created 2012-12-26
     */
    public static void setUri(String uri) {
        THEATRICAL.get().uri = uri;
    }

    /**
     * context in each thread
     */
    private static class ThreadContext {
        /**
         * request start time
         */
        private long startTime;

        /**
         * request uri
         */
        private String uri;

        /**
         * request ip
         */
        private String ip;
    }
}
