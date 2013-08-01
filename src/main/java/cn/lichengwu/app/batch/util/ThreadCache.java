package cn.lichengwu.app.batch.util;

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
     * @author lichengwu
     * @created 2012-12-26
     * 
     * @return thread start time or null
     */
    public static long getStartTime() {
        return THEATRICAL.get().startTime;
    }

    /**
     * set thread start time
     * 
     * @author lichengwu
     * @created 2012-12-26
     * 
     * @param startTime
     */
    public static void setStartTime(long startTime) {
        THEATRICAL.get().startTime = startTime;
    }

    /**
     * get remote request ip
     * 
     * @author lichengwu
     * @created 2012-12-26
     * 
     * @return emote request ip or null
     */
    public static String getIp() {
        return THEATRICAL.get().ip;
    }

    /**
     * set remote request ip
     * 
     * @author lichengwu
     * @created 2012-12-26
     * 
     * @param ip
     */
    public static void setIp(String ip) {
        THEATRICAL.get().ip = ip;
    }

    /**
     * get request uri
     * 
     * @author lichengwu
     * @created 2012-12-26
     * 
     * @return request uri or null
     */
    public static String getUri() {
        return THEATRICAL.get().uri;
    }

    /**
     * set request uri
     * 
     * @author lichengwu
     * @created 2012-12-26
     * 
     * @param uri
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
