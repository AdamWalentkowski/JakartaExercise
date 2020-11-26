package com.eti.pg.task.dto;

import com.eti.pg.task.entity.Task;
import lombok.*;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateTaskRequest {
    private String title;
    private String description;
    private Integer priority;

    public static BiFunction<UpdateTaskRequest, Task, Task> dtoToEntityUpdater() {
        return (request, taskToUpdate) -> {
            taskToUpdate.setTitle(request.getTitle());
            taskToUpdate.setDescription(request.getDescription());
            taskToUpdate.setPriority(request.getPriority());
            return taskToUpdate;
        };
    }
}
