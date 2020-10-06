package com.eti.pg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Task {
    String title;
    String description;
    Integer priority;
    LocalDate creationDate;
    Board board;
    User user;
}
