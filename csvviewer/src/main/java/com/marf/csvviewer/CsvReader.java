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
    private List<String[]> lines;

    public void read(String content) throws IOException {
        read(new StringReader(content));
    }

    public void read(Reader file) throws IOException {
        if (file == null)
            throw new IllegalArgumentException("file may not be empty");

        BufferedReader reader = new BufferedReader(file);
        String headerLine = reader.readLine();
        if (headerLine == null)
            throw new IllegalArgumentException("file may not be empty");

        this.headers = Arrays.asList(headerLine.trim().split(";"));

        this.lines = new ArrayList<>();
        String dataLine = reader.readLine();
        while (dataLine != null) {
            this.lines.add(dataLine.trim().split(";"));
            dataLine = reader.readLine();
        }
    }

    private int[] getColumnLength(List<String[]> lines) {
        int[] length = new int[headers.size()];
        for (int i = 0; i < headers.size(); i++) {
            length[i] = headers.get(i).length();
        }

        for (String[] line : lines) {
            for (int i = 0; i < line.length; i++) {
                length[i] = Math.max(length[i], line[i].length());
            }
        }
        return length;
    }

    private String fill(String source, int length, String filler) {
        return source.length() >= length ? source : fill(source+filler, length, filler);
    }

    @Override
    public String toString() {
        return page(0, Integer.MAX_VALUE);
    }

    private StringBuilder linesToString(int[] length, List<String[]> lines) {
        StringBuilder linesBuilder = new StringBuilder();
        for (String[] line : lines) {
            linesBuilder.append(lineToString(length, line));
        }
        return linesBuilder;
    }

    private String lineToString(int[] length, String[] line) {
        StringBuilder lineBuilder = new StringBuilder();
        for (int i = 0; i < line.length; i++) {
            if (i != 0) {
                lineBuilder.append("|");
            }
            lineBuilder.append(fill(line[i], length[i], " "));
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
        List<String[]> lines = this.lines.subList(firstIndex, lastIndex);

        int[] length = getColumnLength(lines);
        return headerToString(length)+
                headerLineToString(length)+
                linesToString(length, lines);
    }

    public int lastPageNum(int pageSize) {
        return lines.size() / pageSize;
    }

    public static void main(String[] params) throws IOException {
        int pageSize = 3;
        int page = 0;
        String file = "sampledata.txt";

        CsvReader reader = new CsvReader();
        reader.read(new InputStreamReader(CsvReader.class.getResourceAsStream("/"+file)));
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println(reader.page(page, pageSize));
            System.out.println("N(ext page, P(revious page, F(irst page, L(ast page, eX(it");
            String command = input.readLine();
            switch (command) {
                case "x": System.exit(0);
                case "n":
                    page = Math.min(reader.lastPageNum(pageSize), page+1);;
                    break;
                case "p":
                    page = Math.max(0, page-1);
                    break;
                case "f":
                    page = 0;
                    break;
                case "l":
                    page = reader.lastPageNum(pageSize);
                    break;
            }
        }

    }
}
