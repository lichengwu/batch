package oliver.app.batch.service;

import java.net.URL;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import oliver.app.batch.concurrent.TrackingExecutor;
import oliver.app.batch.core.Lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebCrawler
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2013-01-12 PM9:57
 */
public abstract class WebCrawler implements Lifecycle {

    private static Logger log = LoggerFactory.getLogger(WebCrawler.class);

    /**
     * min thread number
     */
    private static final int DEFAULT_MIN_THREAD_NUM = 0;

    /**
     * max thread number
     */
    private static final int DEFAULT_MAX_THREAD_NUM = Runtime.getRuntime().availableProcessors() + 1;

    private TrackingExecutor exec;

    /**
     * store urls
     */
    private Set<URL> urls = new LinkedHashSet<URL>();

    /**
     * set {@link URL} task 4 {@link WebCrawler}
     * 
     * @param urls
     */
    public synchronized void setTask(Collection<URL> urls) {
        if (isRunning()) {
            throw new IllegalStateException("can not set task because : WebCrawler already start");
        }
        this.urls.addAll(urls);
    }

    /**
     * Process the given url, get url from html
     * 
     * @param url
     * @return
     */
    protected abstract List<URL> processUrl(URL url);

    /**
     * custom max thread
     * 
     * @return
     */
    protected abstract int getMaxThreadNum();

    /**
     * custom min thread
     * 
     * @return
     */
    protected abstract int getMinThreadNum();

    /**
     * start the service
     */
    @Override
    public synchronized void start() {

        log.info("start WebCrawler...");

        if (exec != null) {
            throw new IllegalStateException("WebCrawler already start");
        }

        ExecutorService baseExec = Executors.newCachedThreadPool();

        // custom thread pool
        if (baseExec instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor tpe = ((ThreadPoolExecutor) baseExec);
            tpe.setCorePoolSize(getMinThreadNum() < 0 ? DEFAULT_MIN_THREAD_NUM : getMinThreadNum());
            tpe.setMaximumPoolSize(getMaxThreadNum() <= 0 ? DEFAULT_MAX_THREAD_NUM
                    : getMaxThreadNum());
            tpe.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        }

        exec = new TrackingExecutor(baseExec);

        for (URL url : urls) {
            submitCrawlTask(url);
        }

    }

    /**
     * stop the service
     */
    @Override
    public synchronized void stop() {

        log.info("stop WebCrawler...");

        if (exec == null || exec.isTerminated()) {
            throw new IllegalStateException("WebCrawler already stop");
        }

        try {
            saveUnCrawled(exec.shutdownNow());
            if (exec.awaitTermination(10, TimeUnit.SECONDS)) {
                saveUnCrawled(exec.getCancelledTasks());
            }
        } catch (InterruptedException e) {
            log.warn("error occur while stop " + this.getClass().getSimpleName() + " : "
                    + e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            exec = null;
        }
    }

    /**
     * Check whether the service is running
     * 
     * @return
     */
    @Override
    public boolean isRunning() {
        return exec != null && !exec.isTerminated();
    }

    /**
     * submit a {@link CrawlTask} which will run immediately
     * 
     * @param link
     */
    private void submitCrawlTask(URL link) {
        exec.execute(new CrawlTask(link));

    }

    /**
     * save uncrawled task to url set
     * 
     * @param unCrawled
     *            tasks
     */
    private void saveUnCrawled(List<Runnable> unCrawled) {
        for (Runnable task : unCrawled) {
            if (task instanceof CrawlTask) {
                urls.add(((CrawlTask) task).getURL());
            }
        }
    }

    /**
     * CrawlTask
     */
    private class CrawlTask implements Runnable {

        private final URL url;

        public CrawlTask(URL url) {
            this.url = url;
        }

        /**
         * 
         * run task
         * 
         * @see Thread#run()
         */
        @Override
        public void run() {
            for (URL link : processUrl(url)) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
                submitCrawlTask(link);
            }
        }

        /**
         * Get the {@link URL} processed by this task
         * 
         * @return the {@link URL} processed by this task
         */
        public URL getURL() {
            return this.url;
        }
    }

}
