package com.inter.model.files;

import java.io.BufferedReader;

public class FileData {
    private int fileId;
    private String filename;
    private BufferedReader bufferedReader;

    public FileData(int fileId, String filename, BufferedReader bufferedReader) {
        this.filename = filename;
        this.bufferedReader = bufferedReader;
    }

    public String getFilename() {
        return filename;
    }

    public BufferedReader getReader() {
        return bufferedReader;
    }

    @Override
    public String toString() {
        return String.format("(%d, %s)", fileId, filename);
    }
}
