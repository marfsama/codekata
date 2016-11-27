package com.marf.csvviewer;

import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * UnitTest for {@link com.marf.csvviewer.CsvViewer}.
 */
public class CsvViewerTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithPageSizeZero() throws IOException {
        new CsvViewer(0, reader());
    }

    @Test
    public void initialPageShouldBeZero() throws IOException {
        assertThat(new CsvViewer(2, reader()).getPage()).isEqualTo(0);
    }

    @Test
    public void previousPageShouldStopAtFirstPage() throws IOException {
        CsvViewer csvViewer = new CsvViewer(2, reader());
        csvViewer.previousPage();
        assertThat(csvViewer.getPage()).isEqualTo(0);
    }

    @Test
    public void nextPageAndPreviousPageShouldChangePage() throws IOException {
        CsvViewer csvViewer = new CsvViewer(2, reader());
        csvViewer.nextPage();
        assertThat(csvViewer.getPage()).isEqualTo(1);
        csvViewer.nextPage();
        assertThat(csvViewer.getPage()).isEqualTo(2);
        csvViewer.previousPage();
        assertThat(csvViewer.getPage()).isEqualTo(1);
        csvViewer.previousPage();
        assertThat(csvViewer.getPage()).isEqualTo(0);
    }

    @Test
    public void nextPageShouldStopAtLastPage() throws IOException {
        CsvViewer csvViewer = new CsvViewer(3, reader());
        csvViewer.nextPage();
        csvViewer.nextPage();
        assertThat(csvViewer.getPage()).isEqualTo(1);
    }

    @Test
    public void firstPageShouldGoToPageZero() throws IOException {
        CsvViewer csvViewer = new CsvViewer(2, reader());
        csvViewer.nextPage();
        csvViewer.firstPage();
        assertThat(csvViewer.getPage()).isEqualTo(0);
    }

    @Test
    public void lastPageShouldGoToLastPage() throws IOException {
        CsvViewer csvViewer = new CsvViewer(2, reader());
        csvViewer.lastPage();
        assertThat(csvViewer.getPage()).isEqualTo(2);
    }

    @Test
    public void jumpToPageShouldGoToSelectedPage() throws IOException {
        CsvViewer csvViewer = new CsvViewer(2, reader());
        csvViewer.setPage(2);
        assertThat(csvViewer.getPage()).isEqualTo(2);
    }

    @Test
    public void jumpToPageShouldNotSkipPastLastPage() throws IOException {
        CsvViewer csvViewer = new CsvViewer(2, reader());
        csvViewer.setPage(3);
        assertThat(csvViewer.getPage()).isEqualTo(2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void jumpToPageShouldFailWithNegativePage() throws IOException {
        CsvViewer csvViewer = new CsvViewer(2, reader());
        csvViewer.setPage(-1);
    }

    private CsvReader reader() throws IOException {
        CsvReader csvReader = new CsvReader();
        csvReader.read("col1;col2;col3\n1234;1234;1234\n123456;123456;123456\n12345678;12345678;12345678\n1234567890;1234567890;1234567890");
        return csvReader;
    }
}
