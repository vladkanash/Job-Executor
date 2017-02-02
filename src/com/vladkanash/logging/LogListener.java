package com.vladkanash.logging;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by User on 02.02.2017.
 */
public abstract class LogListener {

    private Set<LogLevel> levels = EnumSet.of(LogLevel.INFO, LogLevel.ERROR);
    private MessageFormatter formatter = new DefaultMessageFormatter();

    public void setFormatter(final MessageFormatter formatter) {
        this.formatter = formatter;
    }

    public void setLogLevels(final LogLevel level, final LogLevel... levels) {
        if (level == null) {
            return;
        }

        this.levels.clear();
        this.levels.add(level);
        Collections.addAll(this.levels, levels);
    }

     final void logMessage(final String message, final LogLevel level) {
        if (message == null || message.isEmpty() || level == null) {
            return;
        }
        final String formattedMessage = formatter.format(message, level);
        writeLog(formattedMessage);
    }

    final Set<LogLevel> getLevels() {
        return levels;
    }

    protected abstract void writeLog(final String message);
}
