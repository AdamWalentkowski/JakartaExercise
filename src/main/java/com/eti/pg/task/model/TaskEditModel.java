package com.eti.pg.task.model;

import com.eti.pg.board.entity.Board;
import com.eti.pg.task.entity.Task;
import lombok.*;

import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class TaskEditModel {
    private String title;
    private String description;
    private Integer priority;
    private Board board;

    public static Function<Task, TaskEditModel> entityToModelMapper() {
        return task -> TaskEditModel.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .board(task.getBoard())
                .build();
    }

    public static BiFunction<TaskEditModel, Task, Task> modelToEntityUpdater() {
        return (request, taskToUpdate) -> {
            taskToUpdate.setTitle(request.getTitle());
            taskToUpdate.setDescription(request.getDescription());
            taskToUpdate.setPriority(request.getPriority());
            taskToUpdate.setBoard(request.getBoard());
            return taskToUpdate;
        };
    }
}
