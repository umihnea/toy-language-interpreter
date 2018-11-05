package com.inter.model.files;

import java.io.BufferedReader;

public class FileData {
    private String filename;
    private BufferedReader bufferedReader;

    public FileData(String filename, BufferedReader bufferedReader) {
        this.filename = filename;
        this.bufferedReader = bufferedReader;
    }

    public String getFilename() {
        return filename;
    }

    public BufferedReader getReader() {
        return bufferedReader;
    }
}
