package com.eti.pg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class User {
    String login;
    LocalDate employmentDate;
    Role role;
}
