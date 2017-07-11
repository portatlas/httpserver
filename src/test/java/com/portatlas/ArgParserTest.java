package com.portatlas;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArgParserTest {
    private ArgParser ArgParser;
    private String[] args = new String[]{"-p", "9090", "-d", "/user/path"};
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() throws Exception {
        ArgParser = new ArgParser();
    }

    @Test
    public void testDefaultPort() {
        assertEquals(5000, ArgParser.getPort());
    }

    @Test
    public void testParsePortFromArgs() {
        ArgParser.parseArgs(args);

        assertEquals(9090, ArgParser.getPort());
    }

    @Test
    public void testParseDirFromArgs() {
        ArgParser.parseArgs(args);

        assertEquals("/user/path", ArgParser.getDirectoryPath());
    }

    @Test
    public void testPrintArgsToConsole() {
        System.setOut(new PrintStream(outContent));
        ArgParser.printArgs(5000, "/user/path/");

        assertEquals("Server running on port: 5000\nDirectory Path: /user/path/\n", outContent.toString());
    }
}
