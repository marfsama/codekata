package com.marf.csvviewer;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * reads csv-files.
 */
public class CsvReader {

    private List<String> headers;
    private List<List<String>> lines;

    public void read(String content) throws IOException {
        read(new StringReader(content));
    }

    public void read(Reader file) throws IOException {
        if (file == null)
            throw new IllegalArgumentException("file may not be empty");

        BufferedReader reader = new BufferedReader(file);

        headers = new ArrayList<>();
        headers.add("No.");
        String headerLine = reader.readLine();
        if (headerLine == null)
            throw new IllegalArgumentException("file may not be empty");

        this.headers.addAll(Arrays.asList(headerLine.trim().split(";")));

        this.lines = new ArrayList<>();
        String dataLine = reader.readLine();
        while (dataLine != null) {
            List<String> dataValues = new ArrayList<>();
            dataValues.add((lines.size()+1)+".");
            dataValues.addAll(Arrays.asList(dataLine.trim().split(";")));
            this.lines.add(dataValues);
            dataLine = reader.readLine();
        }
    }

    private int[] getColumnLength(List<List<String>> lines) {
        int[] length = new int[headers.size()];
        for (int i = 0; i < headers.size(); i++) {
            length[i] = headers.get(i).length();
        }

        for (List<String> line : lines) {
            for (int i = 0; i < line.size(); i++) {
                length[i] = Math.max(length[i], line.get(i).length());
            }
        }
        return length;
    }

    private String fill(String source, int length, String filler) {
        return source.length() >= length ? source : fill(source+filler, length, filler);
    }

    private StringBuilder linesToString(int[] length, List<List<String>> lines) {
        StringBuilder linesBuilder = new StringBuilder();
        for (List<String> line : lines) {
            linesBuilder.append(lineToString(length, line));
        }
        return linesBuilder;
    }

    private String lineToString(int[] length, List<String> line) {
        StringBuilder lineBuilder = new StringBuilder();
        for (int i = 0; i < line.size(); i++) {
            if (i != 0) {
                lineBuilder.append("|");
            }
            lineBuilder.append(fill(line.get(i), length[i], " "));
        }
        lineBuilder.append("\n");
        return lineBuilder.toString();
    }

    private String headerLineToString(int[] length) {
        StringBuilder headerLine = new StringBuilder();
        for (int i = 0; i < headers.size(); i++) {
            if (i != 0) {
                headerLine.append("+");
            }
            headerLine.append(fill("", length[i], "-"));
        }
        headerLine.append("\n");
        return headerLine.toString();
    }

    private String headerToString(int[] length) {
        StringBuilder header = new StringBuilder();
        for (int i = 0; i < headers.size(); i++) {
            if (i != 0) {
                header.append("|");
            }
            header.append(fill(headers.get(i), length[i], " "));
        }
        header.append("\n");
        return header.toString();
    }

    public String page(int pageNum, int pageSize) {
        int firstIndex = Math.min(pageSize * pageNum, lines.size());
        int lastIndex = Math.min(pageSize * (pageNum + 1), lines.size());
        List<List<String>> lines = this.lines.subList(firstIndex, lastIndex);

        int[] length = getColumnLength(lines);
        return headerToString(length)+
                headerLineToString(length)+
                linesToString(length, lines);
    }

    public int getSize() {
        return lines.size();
    }

    @Override
    public String toString() {
        return page(0, Integer.MAX_VALUE);
    }
}
