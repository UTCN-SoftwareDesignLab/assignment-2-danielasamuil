package com.example.bookstore.frontoffice.mapper;

import com.example.bookstore.frontoffice.model.Book;
import com.example.bookstore.frontoffice.model.dto.BookDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO bookToDto(Book book);

    Book fromDto(BookDTO book);
}
