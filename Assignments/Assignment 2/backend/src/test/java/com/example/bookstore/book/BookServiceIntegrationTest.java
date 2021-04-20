package com.example.bookstore.book;

import com.example.bookstore.frontoffice.model.Book;
import com.example.bookstore.frontoffice.model.dto.BookDTO;
import com.example.bookstore.frontoffice.repository.BookRepository;
import com.example.bookstore.frontoffice.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void findAll() {
        int nrBooks = 10;
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < nrBooks; i++) {
            Book book = Book.builder()
                    .name("Name " + i)
                    .author("Author" + i)
                    .quantity(1)
                    .build();
            books.add(book);
            bookRepository.save(book);
        }

        List<BookDTO> bookDTOS = bookService.findAll();

        for (int i = 0; i < nrBooks; i++) {
            assertEquals(books.get(i).getId(), bookDTOS.get(i).getId());
            assertEquals(books.get(i).getName(), bookDTOS.get(i).getName());
        }
    }

    @Test
    void createAll() {
        int nrBooks = 10;
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < nrBooks; i++) {
            Book book = Book.builder()
                    .name("Name " + i)
                    .author("Author" + i)
                    .quantity(1)
                    .build();
            books.add(book);
            bookRepository.save(book);
        }

        List<BookDTO> bookDTOS = bookService.findAll();

        for (int i = 0; i < nrBooks; i++) {
            assertEquals(books.get(i).getId(), bookDTOS.get(i).getId());
            assertEquals(books.get(i).getName(), bookDTOS.get(i).getName());
        }
    }

    @Test
    void updateAll() {
        int nrBooks = 10;
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < nrBooks; i++) {
            Book book = Book.builder()
                    .name("Name " + i)
                    .author("Author" + i)
                    .quantity(1)
                    .build();
            books.add(book);

            book.setAuthor("Some other author" + i);
            bookRepository.save(book);
        }

        List<BookDTO> bookDTOS = bookService.findAll();

        for (int i = 0; i < nrBooks; i++) {
            assertEquals(books.get(i).getId(), bookDTOS.get(i).getId());
            assertEquals(books.get(i).getAuthor(), bookDTOS.get(i).getAuthor());
        }
    }

    @Test
    void deleteAll() {
        bookRepository.deleteAll();

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(0, all.size());
    }
}
