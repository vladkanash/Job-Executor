package main.java.com.vladkanash.logging;

import java.util.Date;

/**
 * Created by User on 02.02.2017.
 */
class DefaultMessageFormatter implements MessageFormatter {

    public String format(final String message, final LogLevel level) {
        return new StringBuilder()
                .append(level)
                .append(" ")
                .append(new Date())
                .append(" ")
                .append(message)
                .append("\n")
                .toString();
    }
}
