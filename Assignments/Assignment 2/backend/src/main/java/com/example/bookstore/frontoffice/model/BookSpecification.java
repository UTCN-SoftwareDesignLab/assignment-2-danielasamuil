package com.example.bookstore.frontoffice.model;

import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<Book> similarTitles(String name) {
        return (root, query, cb) -> cb.like(root.get("name"), name);
    }

    public static Specification<Book> similarAuthors(String author) {
        return (root, query, cb) -> cb.like(root.get("author"), author);
    }

    public static Specification<Book> similarGenres(String genre) {
        return (root, query, cb) -> cb.like(root.get("genre"), genre);
    }
}
