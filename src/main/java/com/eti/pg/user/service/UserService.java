package com.eti.pg.user.service;

import com.eti.pg.user.entity.User;
import com.eti.pg.user.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class UserService {
    private UserRepository userRepository;

    @Inject
    @ConfigProperty(name="filepath", defaultValue ="C:\\")
    private String filePath;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.find(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void create(User user) {
        userRepository.create(user);
    }

    public void addAvatar(Long id, InputStream inputStream) {
        userRepository.find(id).ifPresent(user -> {
            try {
                var file = new File(String.format("%s//%s.png", filePath, user.getLogin()));
                FileUtils.copyInputStreamToFile(inputStream, file);
                user.setAvatarPath(file.getAbsolutePath());
                userRepository.update(user);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }
}
