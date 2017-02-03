package main.java.com.vladkanash.logging;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by User on 02.02.2017.
 */
public final class Logger {

    private final Set<LogListener> listeners = new HashSet<>();
    private static Logger logger;

    private Logger() {}

    public static Logger getInstance() {
        synchronized (Logger.class) {
            if (logger == null) {
                logger = new Logger();
            }
        }
        return logger;
    }

    public void log(final LogLevel level, final String message) {
        for (final LogListener listener : listeners) {
            if (listener.getLevels().contains(level)) {
                listener.logMessage(message, level);
            }
        }
    }

    public void registerLogger(final LogListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }
}
