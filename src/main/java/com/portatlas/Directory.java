package com.portatlas;

import java.io.File;
import java.util.ArrayList;

public class Directory {

    public String defaultDir = System.getProperty("user.dir") + "/public/";
    public ArrayList files = new ArrayList();

    public ArrayList listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                files.add(fileEntry.getName());
            }
        }
        return files;
    }

    public boolean hasFile(String fileName) {
        return (files.contains(fileName)) ? true: false;
    }

}
