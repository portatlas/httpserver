package com.portatlas;

import java.io.File;
import java.util.ArrayList;

public class Directory {
    public String pathName;
    public ArrayList<String> files = new ArrayList<String>();

    public Directory() {
        this.pathName = System.getProperty("user.dir") + "/public/";
        this.files = listFilesForFolder(new File(pathName));
    }

    public Directory(String directoryPath) {
        this.pathName = directoryPath;
        this.files = listFilesForFolder(new File(directoryPath));
    }

    public String getPathName() {
        return pathName;
    }

    public ArrayList listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else if(!hasFile(fileEntry.getName())){
                files.add(fileEntry.getName());
            }
        }
        return files;
    }

    public boolean hasFile(String fileName) {
        return (files.contains(fileName));
    }
}
