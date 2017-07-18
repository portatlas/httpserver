package com.portatlas.helpers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArgParserTest {
    private ArgParser argParser;
    private String[] args = new String[]{"-p", "9090", "-d", "/user/path"};
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() throws Exception {
        argParser = new ArgParser();
    }

    @Test
    public void testDefaultPort() {
        assertEquals(5000, argParser.getPort());
    }

    @Test
    public void testParsePortFromArgs() {
        argParser.parseArgs(args);

        assertEquals(9090, argParser.getPort());
    }

    @Test
    public void testParseDirFromArgs() {
        argParser.parseArgs(args);

        assertEquals("/user/path", argParser.getDirectoryPath());
    }

    @Test
    public void testPrintArgsToConsole() {
        System.setOut(new PrintStream(outContent));
        argParser.printArgs(5000, "/user/path/");

        assertEquals("Server running on port: 5000\nDirectory Path: /user/path/\n", outContent.toString());
    }
}
