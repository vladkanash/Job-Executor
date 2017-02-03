package com.vladkanash.logging;

/**
 * Created by User on 02.02.2017.
 */
public class ConsoleLogListener extends LogListener {

    @Override
    protected void writeLog(final String message, final LogLevel level) {
        System.out.print(message);
    }
}
