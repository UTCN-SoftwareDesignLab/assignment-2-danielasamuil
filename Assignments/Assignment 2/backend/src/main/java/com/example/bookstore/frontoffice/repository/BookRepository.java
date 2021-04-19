package com.example.bookstore.frontoffice.repository;

import com.example.bookstore.frontoffice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT book FROM Book book WHERE book.name LIKE %:input% OR book.author LIKE %:input% OR book.genre LIKE %:input%")
    List<Book> findByNameOrAuthorOrGenre(@Param("input") String input);

    Book findByName(String name);
}
