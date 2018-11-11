package com.inter.model.files;

import java.io.BufferedReader;
import java.util.Scanner;

public class FileData {
    private int fileId;
    private String filename;
    private Scanner scanner;

    public FileData(int fileId, String filename, BufferedReader bufferedReader) {
        this.fileId = fileId;
        this.filename = filename;
        this.scanner = new Scanner(bufferedReader);
    }

    public String getFilename() {
        return filename;
    }

    public int nextInt() {
        return this.scanner.nextInt();
    }

    public boolean hasNextInt() {
        return this.scanner.hasNextInt();
    }

    public void close() {
        this.scanner.close();
    }

    @Override
    public String toString() {
        return String.format("(%d, %s)", fileId, filename);
    }
}
