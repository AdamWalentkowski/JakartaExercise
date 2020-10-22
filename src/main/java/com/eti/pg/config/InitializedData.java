package com.eti.pg.config;

import com.eti.pg.Role;
import com.eti.pg.user.entity.User;
import com.eti.pg.user.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Arrays;

@ApplicationScoped
public class InitializedData {
    private final UserService userService;

    @Inject
    public InitializedData(UserService userService) {
        this.userService = userService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private synchronized void init() {
        var initUsers = Arrays.asList(
            User.builder()
                    .login("lordofon1X1")
                    .firstName("Marek")
                    .lastName("Niewazne")
                    .employmentDate(LocalDate.now().minusYears(5))
                    .role(Role.ADMIN)
                    .build(),
            User.builder()
                    .login("siaBaDaBa")
                    .firstName("Kornel")
                    .lastName("Krzywiec")
                    .employmentDate(LocalDate.now().minusYears(3))
                    .role(Role.MAINTAINER)
                    .build(),
            User.builder()
                    .login("toto_Africa")
                    .firstName("Maciek")
                    .lastName("Keicam")
                    .employmentDate(LocalDate.now().minusYears(1))
                    .role(Role.DEVELOPER)
                    .build(),
            User.builder()
                    .login("bartek3212_stazysta")
                    .firstName("Bartek")
                    .lastName("Nudny")
                    .employmentDate(LocalDate.now().minusYears(1))
                    .role(Role.DEVELOPER)
                    .build()
        );
        initUsers.forEach(userService::create);
    }
}