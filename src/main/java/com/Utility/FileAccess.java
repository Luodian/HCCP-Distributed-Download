package com.Utility;

import java.io.*;

public class FileAccess implements Serializable {
    private RandomAccessFile targetFile;

    FileAccess(String fileName, long position) throws IOException {
        this.targetFile = new RandomAccessFile(fileName, "rw");
        this.targetFile.seek(position);
    }

    public synchronized int write(byte[] buffer, int startIndex, int length) {
        int writeLength = -1;
        try {
            targetFile.write(buffer, startIndex, length);
            writeLength = length;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writeLength;
    }
}