package org.example.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LineReader {
    private final BufferedReader reader;
    private String line;

    public LineReader(File file) throws IOException {
        this.reader = new BufferedReader(new FileReader(file));
        readNextLine();
    }

    public boolean readNextLine() throws IOException {
        line = reader.readLine();
        return line != null;
    }

    public String getLine() {
        return line;
    }
}
