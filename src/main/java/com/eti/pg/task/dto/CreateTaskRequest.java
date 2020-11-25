package com.eti.pg.task.dto;

import com.eti.pg.board.entity.Board;
import com.eti.pg.task.entity.Task;
import lombok.*;

import java.time.LocalDate;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class CreateTaskRequest {
    private String title;
    private String description;
    private Integer priority;

    public static BiFunction<CreateTaskRequest, Board, Task> dtoToEntityMapper() {
        return (request, board) -> Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
                .creationDate(LocalDate.now())
                .board(board)
                .build();
    }
}
