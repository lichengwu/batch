package oliver.app.batch.concurrent;

import java.util.*;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * {@link TrackingExecutor} extends {@link AbstractExecutorService} which give
 * you the correct cancelled task when executor shutdown.
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2013-01-07 11:23 PM
 */
public class TrackingExecutor extends AbstractExecutorService {

    private final ExecutorService exec;

    private final Set<Runnable> tasksCancelledAtShutdown = Collections
            .synchronizedSet(new HashSet<Runnable>());

    /**
     * create {@link TrackingExecutor} using an existsing
     * {@link ExecutorService}
     * 
     * @param exec
     */
    public TrackingExecutor(ExecutorService exec) {
        this.exec = exec;
    }

    /**
     * Initiates an orderly shutdown in which previously submitted tasks are
     * executed, but no new tasks will be accepted. Invocation has no additional
     * effect if already shut down.
     * <p/>
     * <p>
     * This method does not wait for previously submitted tasks to complete
     * execution. Use {@link #awaitTermination awaitTermination} to do that.
     * 
     * @throws SecurityException
     *             if a security manager exists and shutting down this
     *             ExecutorService may manipulate threads that the caller is not
     *             permitted to modify because it does not hold
     *             {@link RuntimePermission}<tt>("modifyThread")</tt>, or the
     *             security manager's <tt>checkAccess</tt> method denies access.
     */
    @Override
    public void shutdown() {
        exec.shutdown();
    }

    /**
     * Attempts to stop all actively executing tasks, halts the processing of
     * waiting tasks, and returns a list of the tasks that were awaiting
     * execution.
     * <p/>
     * <p>
     * This method does not wait for actively executing tasks to terminate. Use
     * {@link #awaitTermination awaitTermination} to do that.
     * <p/>
     * <p>
     * There are no guarantees beyond best-effort attempts to stop processing
     * actively executing tasks. For example, typical implementations will
     * cancel via {@link Thread#interrupt}, so any task that fails to respond to
     * interrupts may never terminate.
     * 
     * @return list of tasks that never commenced execution
     * @throws SecurityException
     *             if a security manager exists and shutting down this
     *             ExecutorService may manipulate threads that the caller is not
     *             permitted to modify because it does not hold
     *             {@link RuntimePermission}<tt>("modifyThread")</tt>, or the
     *             security manager's <tt>checkAccess</tt> method denies access.
     */
    @Override
    public List<Runnable> shutdownNow() {
        return exec.shutdownNow();
    }

    /**
     * Returns <tt>true</tt> if this executor has been shut down.
     * 
     * @return <tt>true</tt> if this executor has been shut down
     */
    @Override
    public boolean isShutdown() {
        return exec.isShutdown();
    }

    /**
     * Returns <tt>true</tt> if all tasks have completed following shut down.
     * Note that <tt>isTerminated</tt> is never <tt>true</tt> unless either
     * <tt>shutdown</tt> or <tt>shutdownNow</tt> was called first.
     * 
     * @return <tt>true</tt> if all tasks have completed following shut down
     */
    @Override
    public boolean isTerminated() {
        return exec.isTerminated();
    }

    /**
     * Blocks until all tasks have completed execution after a shutdown request,
     * or the timeout occurs, or the current thread is interrupted, whichever
     * happens first.
     * 
     * @param timeout
     *            the maximum time to wait
     * @param unit
     *            the time unit of the timeout argument
     * 
     * @return <tt>true</tt> if this executor terminated and <tt>false</tt> if
     *         the timeout elapsed before termination
     * @throws InterruptedException
     *             if interrupted while waiting
     */
    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return exec.awaitTermination(timeout, unit);
    }

    /**
     * Executes the given command at some time in the future. The command may
     * execute in a new thread, in a pooled thread, or in the calling thread, at
     * the discretion of the <tt>Executor</tt> implementation.
     * 
     * @param command
     *            the runnable task
     * 
     * @throws java.util.concurrent.RejectedExecutionException
     *             if this task cannot be accepted for execution.
     * @throws NullPointerException
     *             if command is null
     */
    @Override
    public void execute(final Runnable command) {
        exec.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    command.run();
                } finally {
                    if (isShutdown() && Thread.currentThread().isInterrupted()) {
                        tasksCancelledAtShutdown.add(command);
                    }
                }
            }
        });
    }

    /**
     * Get all correct cancelled tasks while shutting down.
     * 
     * @throws IllegalStateException
     *             if {@link TrackingExecutor} is not terminated
     * 
     * @return all cancelled tasks while shutting down
     */
    public List<Runnable> getCancelledTasks() {
        if (!exec.isTerminated()) {
            throw new IllegalStateException(getClass().getSimpleName() + " is running...");
        }
        return new ArrayList<Runnable>(tasksCancelledAtShutdown);
    }
}
