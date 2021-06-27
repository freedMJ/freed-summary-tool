package com.lyt.logonlinemonitor.utils;

import java.util.concurrent.*;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

/**
 * @author chenl
 * @version 1.0
 * @date 2020/6/16 16:17
 */
public class ThreadPoolUtil {

    private static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(2000);
    private static final ExecutorService pool = new ThreadPoolExecutor(2, 30, 2L, TimeUnit.MINUTES,
            queue, Executors.defaultThreadFactory(), new CallerRunsPolicy());

    private static final ScheduledExecutorService COMMON_SCHEDULE_POOL = Executors.newScheduledThreadPool(2);

    public static ScheduledExecutorService getSchedulePool() {
        return COMMON_SCHEDULE_POOL;
    }

    public static ExecutorService getPool() {
        return pool;
    }

    public static int getQueueSize() {
        return queue.size();
    }

    public static void executeSchedule(Runnable thread) {
        getSchedulePool().execute(thread);
    }

    public static void execute(Runnable thread) {
        getPool().execute(thread);
    }
} 
