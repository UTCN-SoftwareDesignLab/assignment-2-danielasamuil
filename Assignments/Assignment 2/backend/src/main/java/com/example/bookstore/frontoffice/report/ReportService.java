package com.example.bookstore.frontoffice.report;

import com.example.bookstore.frontoffice.model.Book;

import java.util.List;

public interface ReportService {

    String export();

    ReportType getType();
}
