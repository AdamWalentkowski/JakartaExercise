package com.eti.pg.task.entity;

import com.eti.pg.board.entity.Board;
import com.eti.pg.user.entity.User;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Task implements Serializable {
    private Long id;
    private String title;
    private String description;
    private Integer priority;
    private LocalDate creationDate;
    private Board board;
    private User user;
}
