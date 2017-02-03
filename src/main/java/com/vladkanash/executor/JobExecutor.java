package com.vladkanash.executor;

/**
 * Created by vladkanash on 2.2.17.
 */
public interface JobExecutor {

    void execute(Runnable job);

    void shutdown();
}
