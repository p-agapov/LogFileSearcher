package com.agapovp.logfilesearcher.model;

import java.io.File;

public class FileItem {

   private File file;
   private String name;

    public FileItem(File file) {
        this.file = file;
        this.name = file.getName();
    }

    @Override
    public String toString() {
        return name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
        this.name = file.getName();
    }

    public String getName() {
        return name;
    }
}
