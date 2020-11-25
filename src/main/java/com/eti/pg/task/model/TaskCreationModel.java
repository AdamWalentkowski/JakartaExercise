package com.eti.pg.task.model;

import com.eti.pg.board.entity.Board;
import com.eti.pg.task.entity.Task;
import lombok.*;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class TaskCreationModel {
    private Long id;
    private String title;
    private String description;
    private Integer priority;
    private LocalDate creationDate;
    private Board board;

    public static Function<TaskCreationModel, Task> modelToEntityMapper() {
        return request ->
            Task.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .priority(request.getPriority())
            .board(request.getBoard())
            .creationDate(LocalDate.now())
            .build();
    }
}
