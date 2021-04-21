package com.example.bookstore.book;

import com.example.bookstore.TestCreationFactory;
import com.example.bookstore.frontoffice.mapper.BookMapper;
import com.example.bookstore.frontoffice.model.Book;
import com.example.bookstore.frontoffice.model.dto.BookDTO;
import com.example.bookstore.frontoffice.repository.BookRepository;
import com.example.bookstore.frontoffice.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository, bookMapper);
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(books.size(), all.size());
    }

    // Not yet working
    @Test
    void findByNameOrAuthorOrGenre() {
        Book book1 = Book.builder()
                .name("Name1")
                .author("Author1")
                .genre("Genre1")
                .build();

        Book book2 = Book.builder()
                .name("Name2")
                .author("Author2")
                .genre("Genre2")
                .build();

        Book book3 = Book.builder()
                .name("Name3")
                .author("Author1")
                .genre("Genre3")
                .build();

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);

        bookService.create(bookMapper.bookToDto(book1));
        bookService.create(bookMapper.bookToDto(book2));
        bookService.create(bookMapper.bookToDto(book3));

        List<BookDTO> books = bookService.findByNameOrAuthorOrGenre("Name1");
        System.out.println(books);

        //Assertions.assertEquals(1, bookService.findByNameOrAuthorOrGenre("Name1").size());
    }

    @Test
    void deleteAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.deleteAll();

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(0, all.size());
    }

    @Test
    void delete() {
        Book book2 = Book.builder()
                .name("Name2")
                .author("Author2")
                .genre("Genre2")
                .build();

        when(bookRepository.save(book2)).thenReturn(book2);

        bookRepository.delete(book2);

        List<BookDTO> all = bookService.findAll();
        Assertions.assertEquals(all.size(), 0);
    }

    @Test
    public void update() {
        Book book2 = Book.builder()
                .name("Name2")
                .author("Author2")
                .genre("Genre2")
                .build();

        BookDTO book = BookDTO.builder()
                .name("Name1")
                .author("Author2")
                .genre("Genre2")
                .quantity(50)
                .build();

        when(bookRepository.findById(book2.getId())).thenReturn(Optional.of(book2));

        bookService.update(book2.getId(), book);
        Assertions.assertEquals(50, book2.getQuantity());
    }

    @Test
    void create() {
        Book book2 = Book.builder()
                .name("Name2")
                .author("Author2")
                .genre("Genre2")
                .build();

        BookDTO book = BookDTO.builder()
                .name("Name1")
                .author("Author2")
                .genre("Genre2")
                .quantity(50)
                .build();

        when(bookMapper.fromDto(book)).thenReturn(book2);
        when(bookMapper.bookToDto(book2)).thenReturn(book);
        when(bookRepository.save(book2)).thenReturn(book2);

        Assertions.assertEquals(book.getId(), bookService.create(book).getId());
    }

    //Not working yet
    /*
    @Test
    public void sellBook(){

        BookDTO book2 = BookDTO.builder()
                .name("Name1")
                .author("Author1")
                .price(10)
                .quantity(2)
                .build();

        bookRepository.save(bookMapper.fromDto(book2));
        Assertions.assertTrue(bookService.sell(book2.getId()));
    } */

    @Test()
    public void findOutOfStock() {
        List<BookDTO> booksOutOfStock = bookService.findOutOfStock();
        Assertions.assertNotNull(booksOutOfStock);
    }

}

