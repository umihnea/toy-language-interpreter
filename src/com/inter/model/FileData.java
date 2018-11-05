package com.inter.model;

import java.io.FileReader;

public class FileData {
    private String filename;
    private FileReader fileReader;

    public FileData() {
        this.filename = "";
        this.fileReader = null;
    }

    public FileData(String filename, FileReader fileReader) {
        this.filename = filename;
        this.fileReader = fileReader;
    }

    public String getFilename() {
        return filename;
    }

    public FileReader getFileReader() {
        return fileReader;
    }

    public void setFileReader(FileReader fileReader) {
        this.fileReader = fileReader;
    }
}
