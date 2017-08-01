package com.portatlas.helpers;

public class LogWriter {
    private String logs;

    public LogWriter() {
        this.logs = "";
    }

    public String getLogs() {
        return logs;
    }

    public void logMessage(String content) {
        logs += content + "\n";
    }
}
