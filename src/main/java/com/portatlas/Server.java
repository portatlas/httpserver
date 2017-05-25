package com.portatlas;

public class Server {

    public static void main(String[] args) {
        ArgParser argParser = new ArgParser();
        argParser.parseArgs(args);
        argParser.printArgs(argParser.port, argParser.dir);
    }

}
