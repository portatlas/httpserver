package com.portatlas.helpers.parser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArgParserTest {
    private String[] args = new String[]{"-p", "9090", "-d", "/user/path"};
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void testDefaultPort() {
        assertEquals("5000", ArgParser.getPort(new String[]{}));
    }

    @Test
    public void testGetPortReturnsPortFromArgs() {
        assertEquals("9090", ArgParser.getPort(args));
    }

    @Test
    public void testGetDefaultDirectory() {
        assertEquals(System.getProperty("user.dir") + "/public/", ArgParser.getDirectoryPath(new String[]{}));
    }

    @Test
    public void testParseDirFromArgs() {
        assertEquals("/user/path", ArgParser.getDirectoryPath(args));
    }

    @Test
    public void testPrintArgsToConsole() {
        System.setOut(new PrintStream(outContent));
        ArgParser.printArgs("5000", "/user/path");

        assertEquals("Server running on port: 5000\nDirectory Path: /user/path\n", outContent.toString());
    }
}
