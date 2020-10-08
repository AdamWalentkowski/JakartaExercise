package com.eti.pg;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Task {
    String title;
    String description;
    Integer priority;
    LocalDate creationDate;
    Board board;
    User user;
}
