package com.example.bookstore.frontoffice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Integer id;
    private String name;
    private String author;
    private String genre;
    private double price;
    private int quantity;
}
