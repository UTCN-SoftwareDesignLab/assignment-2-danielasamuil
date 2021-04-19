package com.example.bookstore.frontoffice.controller;

import com.example.bookstore.frontoffice.model.Book;
import com.example.bookstore.frontoffice.model.dto.BookDTO;
import com.example.bookstore.frontoffice.report.ReportServiceFactory;
import com.example.bookstore.frontoffice.report.ReportType;
import com.example.bookstore.frontoffice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.bookstore.frontoffice.controller.UrlMapping.*;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<BookDTO> findAll() {
        return bookService.findAll();
    }

    @GetMapping(SPECIFIC_BOOK)
    public List<BookDTO> findSpecific(@RequestBody String input) {
        return bookService.findByNameOrAuthorOrGenre(input);
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @PutMapping(ENTITY)
    public BookDTO edit(@PathVariable Integer id, @RequestBody BookDTO book) {
        return bookService.update(id, book);
    }

    @DeleteMapping()
    public void deleteAll() {
        bookService.deleteAll();
    }

    @DeleteMapping(ENTITY)
    public void deleteBook(@PathVariable Integer id) {
        bookService.delete(id);
    }

    @GetMapping(EXPORT_REPORT)
    public void exportReport(@PathVariable ReportType type) {
        reportServiceFactory.getReportService(type).export();
    }

    @PostMapping(ENTITY)
    public ResponseEntity<?> sell(@PathVariable Integer id) {
        if (bookService.sell(id))
            return ResponseEntity.ok("Sold the book");
        else
            return ResponseEntity
                    .badRequest()
                    .body("Out of stock");

    }

}


