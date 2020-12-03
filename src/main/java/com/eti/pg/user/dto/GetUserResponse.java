package com.eti.pg.user.dto;

import com.eti.pg.user.entity.UserRole;
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
public class GetUserResponse {
    private String login;
    private String firstName;
    private String lastName;
    private LocalDate employmentDate;
    private String role;

    public static Function<User, GetUserResponse> entityToDtoMapper() {
        return user -> GetUserResponse.builder()
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .employmentDate(user.getEmploymentDate())
                .role(user.getRole())
                .build();
    }
}
