package com.portatlas;

public class ArgParser {
    public int port = 5000;
    public Directory directory = new Directory();
    public String dir = directory.defaultDir;

    public void parseArgs(String[] args) {
        if (args.length > 0 ){
            for(int i = 0; i < args.length; i++ ){
                if (args[i].equals("-p")){
                    port = Integer.parseInt(args[i+1]);
                } else if (args[i].equals("-d")){
                    dir = args[i+1];
                }
            }
        }
    }

    public int getPort() {
        return port;
    }

    public String getDir() {
        return dir;
    }

    public static void printArgs(int port, String dir) {
        System.out.println("Server running on port: " + port + "\nDirectory Path: " + dir );
    }

}
