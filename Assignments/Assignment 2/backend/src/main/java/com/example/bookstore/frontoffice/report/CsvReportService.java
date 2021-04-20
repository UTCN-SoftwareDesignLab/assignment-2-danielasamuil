package com.example.bookstore.frontoffice.report;

import com.example.bookstore.frontoffice.model.Book;
import com.example.bookstore.frontoffice.model.dto.BookDTO;
import com.example.bookstore.frontoffice.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.example.bookstore.frontoffice.report.ReportType.CSV;

@Service
@AllArgsConstructor
public class CsvReportService implements ReportService {

    private final BookService bookService;

    @Override
    public String export() {
        try {

            List<BookDTO> books = bookService.findOutOfStock();

            FileWriter fileWriter;
            fileWriter = new FileWriter("OutOfStockBooks.csv");
            fileWriter.append("id, title, author, genre, price");
            fileWriter.append("\n");

            for (BookDTO book : books) {
                fileWriter.append(book.getId() + "");
                fileWriter.append(",");
                fileWriter.append(book.getName());
                fileWriter.append(",");
                fileWriter.append(book.getAuthor());
                fileWriter.append(",");
                fileWriter.append(book.getGenre());
                fileWriter.append(",");
                fileWriter.append(book.getPrice() + "");
                fileWriter.append(",");
                fileWriter.append("\n");
            }

            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "CSV!";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
