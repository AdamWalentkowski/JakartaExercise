package com.eti.pg.user.dto;

import com.eti.pg.Role;
import com.eti.pg.user.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class GetUserResponse {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private LocalDate employmentDate;
    private Role role;

    public static Function<User, GetUserResponse> entityToDtoMapper() {
        return user -> GetUserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .employmentDate(user.getEmploymentDate())
                .role(user.getRole())
                .build();
    }
}
