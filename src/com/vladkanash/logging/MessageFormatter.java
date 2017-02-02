package com.vladkanash.logging;

/**
 * Created by User on 02.02.2017.
 */
public interface MessageFormatter {
    String format(final String message, LogLevel level);
}
