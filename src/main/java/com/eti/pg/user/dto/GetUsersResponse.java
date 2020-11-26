package com.eti.pg.user.dto;

import com.eti.pg.user.entity.User;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetUsersResponse {
    @Singular
    private List<GetUserResponse> users;

    public static Function<Collection<User>, GetUsersResponse> entityToDtoMapper() {
        return users -> {
            var response = GetUsersResponse.builder();
            users.stream()
                    .map(user -> GetUserResponse.builder()
                            .id(user.getId())
                            .login(user.getLogin())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .employmentDate(user.getEmploymentDate())
                            .role(user.getRole())
                            .build())
                    .forEach(response::user);
            return response.build();
        };
    }
}
