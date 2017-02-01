package com.vladkanash;

/**
 * Created by vladkanash on 2.2.17.
 */
public interface JobExecutor {

    void addJob(Runnable job);

    int getJobsCount();

    void shutdown();
}
