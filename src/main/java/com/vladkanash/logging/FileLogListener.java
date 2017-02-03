package com.vladkanash.logging;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by User on 02.02.2017.
 */
public class FileLogListener extends LogListener {

    private OutputStreamWriter outputStream = null;
    private String filePath;

    public FileLogListener(final String filePath) {
        this.filePath = filePath;

        try {
            outputStream = new OutputStreamWriter(new FileOutputStream(filePath, true));
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open file " + filePath);
        }
    }

    @Override
    protected void writeLog(final String message, final LogLevel level) {
        try {
            if (null == outputStream) {
                outputStream = new OutputStreamWriter(new FileOutputStream(filePath, true));
            }
            outputStream.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outputStream = null;
    }
}
