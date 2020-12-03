package com.eti.pg.user.service;

import com.eti.pg.user.entity.User;
import com.eti.pg.user.entity.UserRole;
import com.eti.pg.user.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Slf4j
@Stateless
@LocalBean
@NoArgsConstructor
@RolesAllowed({UserRole.ADMIN, UserRole.MAINTAINER, UserRole.DEVELOPER})
public class UserService {
    private UserRepository userRepository;
    private SecurityContext securityContext;
    private Pbkdf2PasswordHash pbkdf;

    @Inject
    @ConfigProperty(name="filepath", defaultValue ="C:\\")
    private String filePath;

    @Inject
    public UserService(UserRepository userRepository, SecurityContext securityContext, Pbkdf2PasswordHash pbkdf) {
        this.userRepository = userRepository;
        this.securityContext = securityContext;
        this.pbkdf = pbkdf;
    }

    public Optional<User> findUserByLogin(String login) {
        return userRepository.find(login);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @PermitAll
    public void createUser(User user) {
        if (!securityContext.isCallerInRole(UserRole.ADMIN)) {
            user.setRole(UserRole.DEVELOPER);
        }
        user.setPassword(pbkdf.generate(user.getPassword().toCharArray()));
        userRepository.create(user);
    }

    public void addAvatar(String login, InputStream inputStream) {
        userRepository.find(login).ifPresent(user -> {
            try {
                var file = new File(String.format("%s//%s.png", filePath, user.getLogin()));
                FileUtils.copyInputStreamToFile(inputStream, file);
                user.setAvatarPath(file.getAbsolutePath());
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    public void deleteAvatar(String login) {
        userRepository.find(login).ifPresent(user -> {
            try {
                Files.delete(Path.of(String.format("%s//%s.png", filePath, user.getLogin())));
                user.setAvatarPath(null);
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
        });
    }
}
