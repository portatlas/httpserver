package com.portatlas.helpers.parser;

import com.portatlas.helpers.writers.ConsoleWriter;

public class ArgParser {
    private static String PORT_FLAG = "-p";
    private static String DIRECTORY_FLAG = "-d";
    private static String port = "5000";
    private static String directoryPath = System.getProperty("user.dir") + "/public/";

    public static String getPort(String[] args) {
        return parseArgs(args, PORT_FLAG, port);
    }

    public static String getDirectoryPath(String[] args) {
        return parseArgs(args, DIRECTORY_FLAG, directoryPath);
    }

    private static String parseArgs(String[] args, String flag, String defaultValue) {
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++ ) {
                if (args[i].equals(flag)) {
                    return args[i+1];
                }
            }
        }
        return defaultValue;
    }

    public static void printArgs(String port, String directoryPath) {
        ConsoleWriter.write("Server running on port: " + port + "\nDirectory Path: " + directoryPath );
    }
}
