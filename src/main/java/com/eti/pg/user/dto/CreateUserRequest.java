package com.eti.pg.user.dto;

import com.eti.pg.user.Role;
import com.eti.pg.user.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserRequest {
    private String login;
    private String firstName;
    private String lastName;
    private LocalDate employmentDate;
    private Role role;
    private String avatarPath;

    public static Function<CreateUserRequest, User> dtoToEntityMapper() {
        return request -> User.builder()
                .login(request.getLogin())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .employmentDate(request.employmentDate)
                .role(request.role)
                .avatarPath(request.getAvatarPath())
                .build();
    }
}
