package com.eti.pg;

import com.eti.pg.user.entity.User;
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
