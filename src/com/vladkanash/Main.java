package com.vladkanash;

public class Main {

    public static void main(String[] args) {
	    MyJobExecutor jobExecutor = new MyJobExecutor();

	    jobExecutor.addJob(() -> System.out.println("I am job number 1"));
	    jobExecutor.addJob(() -> System.out.println("I am job number 2"));
	    jobExecutor.addJob(() -> System.out.println("I am job number 3"));
	    jobExecutor.addJob(() -> {
	        System.out.println("I am job number 4");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        jobExecutor.addJob(() -> System.out.println("I am job number 21"));
        jobExecutor.addJob(() -> System.out.println("I am job number 22"));
        jobExecutor.addJob(() -> System.out.println("I am job number 23"));
        jobExecutor.addJob(() -> System.out.println("I am job number 24"));

        jobExecutor.addJob(() -> System.out.println("I am job number 31"));
        jobExecutor.addJob(() -> System.out.println("I am job number 32"));
        jobExecutor.addJob(() -> System.out.println("I am job number 33"));
        jobExecutor.addJob(() -> System.out.println("I am job number 34"));

        jobExecutor.addJob(() -> System.out.println("I am job number 41"));
        jobExecutor.addJob(() -> System.out.println("I am job number 42"));
        jobExecutor.addJob(() -> System.out.println("I am job number 43"));
        jobExecutor.addJob(() -> System.out.println("I am job number 44"));

        jobExecutor.addJob(() -> System.out.println("I am job number 51"));
        jobExecutor.addJob(() -> System.out.println("I am job number 52"));
        jobExecutor.addJob(() -> System.out.println("I am job number 53"));
        jobExecutor.addJob(() -> System.out.println("I am job number 54"));

	    jobExecutor.shutdown();
    }
}
