package com.portatlas.helpers;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class LogWriterTest {
    private LogWriter logWriter;

    @Before
    public void setUp() {
        logWriter = new LogWriter();
    }

    @Test
    public void testLogsStringsToLogger() {
        String firstLog = "hello";
        String secondLog = "world";

        logWriter.logMessage(firstLog);
        logWriter.logMessage(secondLog);

        assertEquals(firstLog + "\n" + secondLog + "\n", logWriter.getLogs());
    }
}