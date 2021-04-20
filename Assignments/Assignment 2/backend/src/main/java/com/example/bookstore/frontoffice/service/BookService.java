package com.example.bookstore.frontoffice.service;

import com.example.bookstore.frontoffice.mapper.BookMapper;
import com.example.bookstore.frontoffice.model.Book;
import com.example.bookstore.frontoffice.model.BookSpecification;
import com.example.bookstore.frontoffice.model.dto.BookDTO;
import com.example.bookstore.frontoffice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private Book findById(Integer id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public List<BookDTO> findAll() {

        return bookRepository.findAll().stream().
                map(bookMapper::bookToDto)
                .collect(Collectors.toList());
    }

    public List<BookDTO> findByNameOrAuthorOrGenre(String input) {

        return bookRepository.findAll(BookSpecification.similarAuthors(input).or(BookSpecification.similarTitles(input)).or(BookSpecification.similarGenres(input))).stream().
                map(bookMapper::bookToDto)
                .collect(Collectors.toList());
    }

    public void deleteAll() {

        bookRepository.deleteAll();
    }

    public void delete(int id) {

        bookRepository.deleteById(id);
    }

    public BookDTO update(Integer id, BookDTO bookDto) {

        Book book = findById(id);

        book.setName(bookDto.getName());
        book.setAuthor(bookDto.getAuthor());
        book.setGenre(bookDto.getGenre());
        book.setPrice(bookDto.getPrice());
        book.setQuantity(bookDto.getQuantity());

        return bookMapper.bookToDto(
                bookRepository.save(book)
        );
    }

    public BookDTO create(BookDTO bookDto) {
        return bookMapper.bookToDto(bookRepository.save(
                bookMapper.fromDto(bookDto)));
    }

    public boolean sell(Integer id) {

        Book book = findById(id);

        if (book.getQuantity() - 1 > 0)
            book.setQuantity(book.getQuantity() - 1);
        else
            return false;

        bookRepository.save(book);
        return true;
    }

    public List<BookDTO> findOutOfStock() {
        List<BookDTO> result = new ArrayList<>();

        List<BookDTO> books = bookRepository.findAll().stream().map(bookMapper::bookToDto)
                .collect(Collectors.toList());

        for (BookDTO book : books)
            if (book.getQuantity() == 0)
                result.add(book);

        return result;
    }
}
