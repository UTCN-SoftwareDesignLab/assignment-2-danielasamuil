package com.example.bookstore.frontoffice.user.mapper;

import com.example.bookstore.frontoffice.user.dto.UserDto;
import com.example.bookstore.frontoffice.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "name", source = "user.username")
    })
    UserDto userMinimalFromUser(User user);

    @Mappings({
            @Mapping(target = "username", source = "name")
    })
    User fromDto(UserDto userDto);
}