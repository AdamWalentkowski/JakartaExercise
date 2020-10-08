package com.eti.pg;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class User {
    String login;
    LocalDate employmentDate;
    Role role;
}
