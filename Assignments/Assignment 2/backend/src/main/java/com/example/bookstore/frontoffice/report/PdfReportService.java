package com.example.bookstore.frontoffice.report;

import com.example.bookstore.frontoffice.model.Book;
import com.example.bookstore.frontoffice.model.dto.BookDTO;
import com.example.bookstore.frontoffice.service.BookService;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.example.bookstore.frontoffice.report.ReportType.PDF;

@Service
@AllArgsConstructor
public class PdfReportService implements ReportService {

    private final BookService bookService;

    @Override
    public String export() {
        try {

            List<BookDTO> books = bookService.findOutOfStock();

            PDDocument document = new PDDocument();
            PDPage page = new PDPage();

            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.COURIER, 10);

            for (BookDTO book : books) {

                contentStream.showText("id: " + book.getId());
                contentStream.newLine();
                contentStream.showText("title: " + book.getName());
                contentStream.newLine();
                contentStream.showText("author: " + book.getAuthor());
                contentStream.newLine();
                contentStream.showText("genre: " + book.getGenre());
                contentStream.newLine();
                contentStream.showText("price: " + book.getPrice());
                contentStream.newLine();
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();

            document.save("C:\\Users\\samui\\OneDrive\\Desktop\\year 3, sem 2\\DS\\Lab-Assignments\\assignment-2-danielasamuil\\Assignments\\Assignment 2\\backend\\OutOfStockBooks.pdf");
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "PDF!";
    }

    @Override
    public ReportType getType() {
        return PDF;
    }
}
