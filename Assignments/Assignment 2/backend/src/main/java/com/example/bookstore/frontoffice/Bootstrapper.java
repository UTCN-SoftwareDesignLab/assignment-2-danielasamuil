package com.example.bookstore.frontoffice;

import com.example.bookstore.frontoffice.model.Book;
import com.example.bookstore.frontoffice.repository.BookRepository;
import com.example.bookstore.frontoffice.security.AuthService;
import com.example.bookstore.frontoffice.security.dto.SignupRequest;
import com.example.bookstore.frontoffice.user.model.ERole;
import com.example.bookstore.frontoffice.user.model.Role;
import com.example.bookstore.frontoffice.user.repository.RoleRepository;
import com.example.bookstore.frontoffice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final BookRepository bookRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            bookRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("alex@email.com")
                    .username("alex")
                    .password("WooHoo1!")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("alex1@email.com")
                    .username("alex1")
                    .password("WooHoo1!")
                    .roles(Set.of("USER"))
                    .build());

            Book b = Book.builder()
                    .name("Title1")
                    .author("Author1")
                    .genre("Genre1")
                    .price(10)
                    .quantity(0)
                    .build();

            Book b1 = Book.builder()
                    .name("Title2")
                    .author("Author1")
                    .genre("Genre2")
                    .price(30)
                    .quantity(0)
                    .build();

            bookRepository.save(b);
            bookRepository.save(b1);
        }
    }
}
