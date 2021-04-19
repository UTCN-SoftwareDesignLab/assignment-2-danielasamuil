package com.example.bookstore.report;

import com.example.bookstore.frontoffice.report.ReportService;
import com.example.bookstore.frontoffice.report.ReportServiceFactory;
import com.example.bookstore.frontoffice.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.bookstore.frontoffice.report.ReportType.CSV;
import static com.example.bookstore.frontoffice.report.ReportType.PDF;

@SpringBootTest
class ReportServiceFactoryTest {

    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Test
    void getReportService() {
        ReportService csvReportService = reportServiceFactory.getReportService(CSV);
        Assertions.assertEquals("CSV!", csvReportService.export());

        ReportService pdfReportService = reportServiceFactory.getReportService(PDF);
        Assertions.assertEquals("PDF!", pdfReportService.export());
    }
}
