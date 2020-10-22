package com.eti.pg.user.entity;

import com.eti.pg.Role;
import lombok.*;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class User implements Serializable {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private LocalDate employmentDate;
    private Role role;
    private String avatarPath;
}
