package com.portatlas;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ArgParserTest {

    private ArgParser ArgParser;
    private String[] args = new String[]{"-p", "9090", "-d", "/user/path"};

    @Before
    public void setUp() throws Exception {
        ArgParser = new ArgParser();
    }

    @Test
    public void testDefaultPort() throws Exception {

        assertEquals(5000, ArgParser.getPort());
    }

    @Test
    public void testDefaultDir() throws Exception {

        assertEquals(System.getProperty("user.dir") + "/public/", ArgParser.getDir());
    }

    @Test
    public void testParsePortFromArgs() throws Exception {
        ArgParser.parseArgs(args);

        assertEquals(9090, ArgParser.getPort());
    }

    @Test
    public void testParseDirFromArgs() throws Exception {
        ArgParser.parseArgs(args);

        assertEquals("/user/path", ArgParser.getDir());
    }

    @Test
    public void testPrintArgsToConsole() throws Exception {
        System.setOut(new PrintStream(outContent));
        ArgParser.printArgs(5000, "/user/path/");

        assertEquals("Server running on port: 5000\nDirectory Path: /user/path/\n", outContent.toString());
    }

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
}
