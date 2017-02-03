package com.vladkanash;

import main.java.com.vladkanash.executor.JobExecutor;
import main.java.com.vladkanash.executor.SingleThreadJobExecutor;
import com.vladkanash.logging.*;
import main.java.com.vladkanash.logging.*;

public class Main {

    public static void main(String[] args) {
        Runnable stoppingJob = () -> {
            System.out.println("I am job number 4");
            throw new ClassCastException();
        };

        Logger logger = Logger.getInstance();

        FileLogListener fileLogListener = new FileLogListener("log.txt");
        FileLogListener executorLogListener = new FileLogListener("executor_log.txt");

        fileLogListener.setLogLevels(LogLevel.INFO, LogLevel.DEBUG, LogLevel.ERROR);
        executorLogListener.setLogLevels(
                LogLevel.INFO,
                LogLevel.ERROR,
                LogLevel.DEBUG,
                LogLevel.WARN);

        LogListener consoleListener = new ConsoleLogListener();
        consoleListener.setLogLevels(LogLevel.ERROR);

        logger.registerLogger(fileLogListener);
        logger.registerLogger(consoleListener);
        logger.registerLogger(executorLogListener);

        Runnable simpleJob = () -> System.out.println("Simple job");

	    final JobExecutor jobExecutor = new SingleThreadJobExecutor();
	    final JobExecutor jobExecutor2 = new SingleThreadJobExecutor();

	    logger.log(LogLevel.DEBUG, "Message 1");

        Runnable executorJob = () -> {
            jobExecutor.execute(() -> System.out.println("I am job number 166"));
            jobExecutor.execute(() -> System.out.println("I am job number 167"));
            jobExecutor.execute(() -> System.out.println("I am job number 168"));
            jobExecutor.execute(() -> System.out.println("I am job number 169"));
        };

        new Thread(executorJob).start();

        logger.log(LogLevel.ERROR, "message2");

	    jobExecutor.execute(() -> System.out.println("I am job number 1"));
        jobExecutor.execute(() -> System.out.println("I am job number 1"));
	    jobExecutor.execute(() -> System.out.println("I am job number 2"));
	    jobExecutor.execute(() -> System.out.println("I am job number 3"));
        jobExecutor.execute(() -> System.out.println("I am job number 2"));
        jobExecutor.execute(() -> System.out.println("I am job number 3"));

	    jobExecutor.execute(() -> {
	        System.out.println("I am job number 4");
            try {
                Thread.sleep(4000);
                logger.log(LogLevel.ERROR, "Finished waiting");

            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        });

	    jobExecutor.execute(stoppingJob);

        logger.log(LogLevel.ERROR, "message3");
        logger.log(LogLevel.ERROR, "Info message");

//	    jobExecutor.shutdown();
        System.out.println("I am main thread");

        jobExecutor.execute(() -> System.out.println("I am job number 51"));

        jobExecutor.shutdown();
        jobExecutor2.shutdown();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            fileLogListener.reset();
            executorLogListener.reset();
        }
    }
}
