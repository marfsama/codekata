package com.marf.csvviewer;


import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvReaderTest {
    @Test(expected = IllegalArgumentException.class)
    public void nullReaderShouldFail() throws IOException {
        CsvReader csvReader = new CsvReader();
        csvReader.read((Reader) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyFileShouldFail() throws IOException {
        CsvReader csvReader = new CsvReader();
        csvReader.read("");
    }

    @Test
    public void headersShouldBeRead() throws IOException {
        CsvReader csvReader = new CsvReader();

        csvReader.read("Name;Age;City");
        assertThat(csvReader.toString()).contains("Name","Age","City");
    }

    @Test
    public void headerShouldHaveLineUnterneath() throws IOException {
        CsvReader csvReader = new CsvReader();

        csvReader.read("Name;Age;City");
        assertThat(csvReader.toString()).contains("--","-+-");
    }

    @Test
    public void firstLineShouldBeRead() throws IOException {
        CsvReader csvReader = new CsvReader();
        csvReader.read("Name;Age;City\n" +"Peter;42;NewYork");
        assertThat(csvReader.toString()).contains("Peter","42","NewYork");
    }

    @Test
    public void secondLineShouldBeRead() throws IOException {
        CsvReader csvReader = new CsvReader();
        csvReader.read("Name;Age;City\nPeter;42;NewYork\nPaul;57;London");
        assertThat(csvReader.toString()).contains("Paul","42","NewYork");
    }

    @Test
    public void headerShouldBeSpacedWithLongestField() throws IOException {
        CsvReader csvReader = new CsvReader();
        csvReader.read("Col1;Col2;Col3;Col4;Col5;Col6;Col7\n1;12;123;1234;12345;123456;1234567");
        assertThat(csvReader.toString())
                .contains("Col1|Col2|Col3|Col4|Col5 |Col6  |Col7   ");
    }

    @Test
    public void headerLineShouldBeSpacedWithLongestField() throws IOException {
        CsvReader csvReader = new CsvReader();
        csvReader.read("Col1;Col2;Col3;Col4;Col5;Col6;Col7\n1;12;123;1234;12345;123456;1234567");
        assertThat(csvReader.toString())
                .contains("----+----+----+----+-----+------+-------");
    }
    @Test
    public void dataLineShouldBeSpacedWithLongestField() throws IOException {
        CsvReader csvReader = new CsvReader();
        csvReader.read("1;12;123;1234;12345;123456;1234567\n1;2;3;4;5;6;7");
        assertThat(csvReader.toString())
                .contains("1|2 |3  |4   |5    |6     |7      ");
    }

    @Test
    public void oneLinePagingShouldDisplayOnlyOneRow() throws IOException {
        CsvReader csvReader = new CsvReader();
        csvReader.read("col1;col2;col3\n1a;1b;1c\n2a;2b;2c\n3a;3b;3c\n4a;4b;4c");
        assertThat(csvReader.page(0,1))
                .contains("1a","1b","1c").hasLineCount(4);
    }

    @Test
    public void twoLinePagingShouldDisplayTowRow() throws IOException {
        CsvReader csvReader = new CsvReader();
        csvReader.read("col1;col2;col3\n1a;1b;1c\n2a;2b;2c\n3a;3b;3c\n4a;4b;4c");
        assertThat(csvReader.page(0,2))
                .contains("1a","1b","1c")
                .contains("2a","2b","2c")
                .hasLineCount(5);
    }

    @Test
    public void secondPageShouldDisplayMoreRows() throws IOException {
        CsvReader csvReader = new CsvReader();
        csvReader.read("col1;col2;col3\n1a;1b;1c\n2a;2b;2c\n3a;3b;3c\n4a;4b;4c");
        assertThat(csvReader.page(1,2))
                .contains("3a","3b","3c")
                .contains("4a","4b","4c");
    }

    @Test
    public void lastPageMayDisplayLessLinesThanPageSize() throws IOException {
        CsvReader csvReader = new CsvReader();
        csvReader.read("col1;col2;col3\n1a;1b;1c\n2a;2b;2c\n3a;3b;3c\n4a;4b;4c");
        assertThat(csvReader.page(1,3))
                .contains("4a","4b","4c")
                .hasLineCount(4);
    }

    @Test
    public void spacingShouldBeLocalToPage() throws IOException {
        CsvReader csvReader = new CsvReader();
        csvReader.read("col1;col2;col3\n1234;1234;1234\n123456;123456;123456\n12345678;12345678;12345678\n1234567890;1234567890;1234567890");
        assertThat(csvReader.page(0,2))
                .contains("1234  |1234  |1234  ");
    }

    @Test
    public void headerShouldHaveNoColumn() throws IOException {
        CsvReader csvReader = new CsvReader();
        csvReader.read("col1;col2;col3\n1234;1234;1234\n123456;123456;123456\n12345678;12345678;12345678\n1234567890;1234567890;1234567890");
        assertThat(csvReader.page(0,2))
                .contains("No.");
    }

    @Test
    public void noColumnShouldStartWithOne() throws IOException {
        CsvReader csvReader = new CsvReader();
        csvReader.read("col1;col2;col3\n1234;1234;1234\n123456;123456;123456\n12345678;12345678;12345678\n1234567890;1234567890;1234567890");
        assertThat(csvReader.page(0,1))
                .contains("1.");
    }

    @Test
    public void noColumnShouldBeConsecutive() throws IOException {
        CsvReader csvReader = new CsvReader();
        csvReader.read("col1;col2;col3\n1234;1234;1234\n123456;123456;123456\n12345678;12345678;12345678\n1234567890;1234567890;1234567890");
        assertThat(csvReader.page(0,2))
                .contains("2.");
    }

    @Test
    public void noColumnShouldAbsoluteInPage() throws IOException {
        CsvReader csvReader = new CsvReader();
        csvReader.read("col1;col2;col3\n1234;1234;1234\n123456;123456;123456\n12345678;12345678;12345678\n1234567890;1234567890;1234567890");
        assertThat(csvReader.page(1,2))
                .contains("3.");
    }

    @Test
    public void currentPageAndMaxPageShouldBeShown() throws IOException {
        CsvReader csvReader = new CsvReader();
        csvReader.read("col1;col2;col3\n1234;1234;1234\n123456;123456;123456\n12345678;12345678;12345678\n1234567890;1234567890;1234567890");
        assertThat(csvReader.page(0,2))
                .contains("Page 1 of 2");
    }

    @Test
    public void incompleteLastPageShouldBeCountedTowardsMaxPage() throws IOException {
        CsvReader csvReader = new CsvReader();
        csvReader.read("col1;col2;col3\n1234;1234;1234\n123456;123456;123456\n12345678;12345678;12345678\n1234567890;1234567890;1234567890\n1;2;3");
        assertThat(csvReader.page(0,2))
                .contains("Page 1 of 3");
    }
}
