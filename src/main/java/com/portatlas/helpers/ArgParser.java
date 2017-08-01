package com.portatlas.helpers;

import com.portatlas.Directory;

public class ArgParser {
    private int port = 5000;
    private Directory directory = new Directory();
    private String directoryPath = directory.pathName;

    public void parseArgs(String[] args) {
        if (args.length > 0 ){
            for(int i = 0; i < args.length; i++ ){
                if (args[i].equals("-p")){
                    port = Integer.parseInt(args[i+1]);
                } else if (args[i].equals("-d")){
                    directoryPath = args[i+1];
                }
            }
        }
    }

    public int getPort() {
        return port;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public static void printArgs(int port, String dir) {
        System.out.println("Server running on port: " + port + "\nDirectory Path: " + dir );
    }
}
