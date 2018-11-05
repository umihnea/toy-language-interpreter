package com.inter.model.files;

import java.io.BufferedReader;

public class FileData {
    private BufferedReader bufferedReader;

    public FileData() {
        this.bufferedReader = null;
    }

    public FileData(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public BufferedReader getReader() {
        return bufferedReader;
    }
}
