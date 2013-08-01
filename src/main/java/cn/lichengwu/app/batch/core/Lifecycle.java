package cn.lichengwu.app.batch.core;

/**
 * Interface defining methods for start/stop lifecycle control.
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2013-01-12 PM9:44
 */
public interface Lifecycle {

    /**
     * start the service
     */
    void start();

    /**
     * stop the service
     */
    void stop();

    /**
     * Check whether the service is running
     * 
     * @return
     */
    boolean isRunning();
}
