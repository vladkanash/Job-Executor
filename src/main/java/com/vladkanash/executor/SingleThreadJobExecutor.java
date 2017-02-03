package main.java.com.vladkanash.executor;

import java.util.ArrayDeque;
import java.util.Queue;

public class SingleThreadJobExecutor implements JobExecutor {

    private final Queue<Runnable> jobQueue = new ArrayDeque<>();
    private Thread executionThread = new Thread(new Worker());

    @Override
    public void execute(Runnable job) {
        synchronized (jobQueue) {
            jobQueue.add(job);
            jobQueue.notify();
        }
    }

    public SingleThreadJobExecutor() {
        executionThread.start();
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
                runJob(job);
            }
        }

        private Runnable getJobFromQueue() {
            Runnable job = jobQueue.poll();
            if (job == null) {
                try {
                    jobQueue.wait();
                } catch (InterruptedException e) {
                    running = false;
                }
            }
            return job;
        }

        private void runJob(final Runnable job) {
            if (job == null) {
                return;
            }
            try {
                job.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
