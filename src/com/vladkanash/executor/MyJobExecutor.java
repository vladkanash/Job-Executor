package com.vladkanash.executor;

import com.vladkanash.logging.LogLevel;
import com.vladkanash.logging.Logger;

import java.util.ArrayDeque;
import java.util.Queue;

public class MyJobExecutor implements JobExecutor {

    private final Queue<Runnable> jobQueue = new ArrayDeque<>();
    private final Logger logger = Logger.getInstance();
    private Thread executionThread;

    @Override
    public void execute(Runnable job) {
        synchronized (jobQueue) {
            jobQueue.add(job);
            logger.log(LogLevel.INFO, "New job added, total jobs waiting for execution: " + jobQueue.size());
            jobQueue.notify();
        }

        if (executionThread == null || !executionThread.isAlive()) {
            executionThread = new Thread(new Worker());
            executionThread.start();
        }
    }

    @Override
    public int getJobsCount() {
        synchronized (jobQueue) {
            return jobQueue.size();
        }
    }

    @Override
    public void shutdown() {
        if (null != executionThread && !executionThread.isInterrupted()) {
            executionThread.interrupt();
        }
    }

    private final class Worker implements Runnable {

        private boolean running = true;

        @Override
        public void run() {
            Runnable job;
            while (running) {
                synchronized (jobQueue) {
                    job = getJobFromQueue();
                }
                logger.log(LogLevel.INFO, "Starting job execution..");
                runJob(job);
            }
        }

        private Runnable getJobFromQueue() {
            Runnable job = jobQueue.poll();
            if (null == job) {
                try {
                    jobQueue.wait();
                } catch (InterruptedException e) {
                    running = false;
                }
            }
            return job;
        }

        private void runJob(final Runnable job) {
            if (null == job) {
                return;
            }
            try {
                job.run();
            } catch (Exception e) {
                e.printStackTrace();
                ////
            }
        }
    }

}
