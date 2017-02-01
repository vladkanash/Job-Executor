package com.vladkanash;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class MyJobExecutor {

    private final Queue<Runnable> jobQueue = new ArrayDeque<>();
    private final List<Thread> executionThreads = new ArrayList<>();

    void addJob(Runnable job) {
        synchronized (jobQueue) {
            //run this job
            jobQueue.add(job);
            jobQueue.notifyAll();
        }
    }

    void shutdown() {
        executionThreads.forEach(Thread::interrupt);
    }

    MyJobExecutor() {
        for (int i = 0; i < 4; i++) {
            Thread thread = new Thread(new ExecutionThread());
            executionThreads.add(thread);
            thread.start();
        }
    }

    private class ExecutionThread implements Runnable {

        @Override
        public void run() {
            synchronized (jobQueue) {
                Runnable job;
                while (true) {
                    job = jobQueue.poll();
                    if (null != job) {
                        System.out.print("Running thread #" + Thread.currentThread().getName());
                        job.run();
                    } else {

                        try {
                            jobQueue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("I am interrupted!");
                            return;
                        }
                    }
                }
            }
        }


    }
}
