package com.vladkanash;

import java.util.ArrayDeque;
import java.util.Queue;

class MyJobExecutor implements JobExecutor {

    private final Queue<Runnable> jobQueue = new ArrayDeque<>();
    private final Thread executionThread = new Thread(new Worker());

    @Override
    public void addJob(Runnable job) {
        synchronized (jobQueue) {
            jobQueue.add(job);
            jobQueue.notify();
        }

        if (!executionThread.isAlive()) {
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
        executionThread.interrupt();
    }

    MyJobExecutor() {
//        executionThread.setDaemon(true);
    }

    private final class Worker implements Runnable {

        private boolean running = true;

        @Override
        public void run() {
            Runnable job;
            while (running) {
                synchronized (jobQueue) {
                    job = getJob();
                }
                runJob(job);
            }
        }

        private Runnable getJob() {
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
            if (null != job) {
                try {
                    job.run();
                } catch (Exception e) {
                    e.printStackTrace();
                    ////
                }
            }
        }
    }

}
