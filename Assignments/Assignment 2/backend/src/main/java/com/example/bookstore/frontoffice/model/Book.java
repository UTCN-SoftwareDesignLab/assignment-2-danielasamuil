package com.example.bookstore.frontoffice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(length = 512)
    private String genre;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int quantity;
}
