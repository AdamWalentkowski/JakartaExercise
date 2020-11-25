package com.eti.pg.user.dto;

import com.eti.pg.board.BoardScope;
import com.eti.pg.board.Role;
import com.eti.pg.board.entity.Board;
import com.eti.pg.user.entity.User;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
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
