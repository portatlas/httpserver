package com.portatlas.helpers.writers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ConsoleWriterTest {
    @Test
    public void testConsoleWriterWritesToConsole() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ConsoleWriter.write("hello");
        assertEquals("hello\n", outContent.toString());
    }

}
