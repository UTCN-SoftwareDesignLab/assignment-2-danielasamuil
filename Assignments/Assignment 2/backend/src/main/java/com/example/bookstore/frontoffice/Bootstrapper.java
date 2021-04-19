package com.example.bookstore.frontoffice;

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
                    .email("samuildaniela@yahoo.com")
                    .username("daniela")
                    .password("Password1")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("samuildaniela1@yahoo.com")
                    .username("daniela1")
                    .password("Password2")
                    .roles(Set.of("USER"))
                    .build());
        }
    }
}
