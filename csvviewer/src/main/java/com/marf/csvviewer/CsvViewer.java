package com.marf.csvviewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * displays files read by {@link CsvReader}.
 */
public class CsvViewer {

    private final int pageSize;
    private final CsvReader reader;
    private int page = 0;

    public CsvViewer(int pageSize, CsvReader reader) {
        if (pageSize < 1)
            throw new IllegalArgumentException("pagesize must be > 0");
        this.pageSize = pageSize;
        this.reader = reader;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page < 0)
            throw new IllegalArgumentException("page should be >= 0");
        this.page = Math.min(page, getLastPage());
    }

    public void previousPage() {
        page = Math.max(0, page-1);
    }

    public void nextPage() {
        page = Math.min(getLastPage(), page+1);
    }

    public void firstPage() {
        page = 0;
    }

    public void lastPage() {
        page = getLastPage();
    }

    private int getLastPage() {
        return reader.getSize() / pageSize;
    }

    public void printTable() {
        System.out.println(reader.page(getPage(), pageSize));
        System.out.println("N(ext page, P(revious page, F(irst page, L(ast page, J(ump to page, eX(it");
    }



    public static void main(String[] params) throws IOException {
        int pageSize = 3;
        String file = "sampledata.txt";

        CsvReader reader = new CsvReader();
        reader.read(new InputStreamReader(CsvReader.class.getResourceAsStream("/"+file)));

        CsvViewer viewer = new CsvViewer(pageSize, reader);

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            viewer.printTable();
            String command = input.readLine();
            switch (command) {
                case "x": System.exit(0);
                case "n":
                    viewer.nextPage();
                    break;
                case "p":
                    viewer.previousPage();
                    break;
                case "f":
                    viewer.firstPage();
                    break;
                case "l":
                    viewer.lastPage();
                    break;
                case "j":
                    System.out.print("page: ");
                    int page = Integer.valueOf(input.readLine())-1;
                    viewer.setPage(page);
                    break;
            }
        }
    }

}
