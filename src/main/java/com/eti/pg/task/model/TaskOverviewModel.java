package com.eti.pg.task.model;

import com.eti.pg.task.entity.Task;
import lombok.*;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class TaskOverviewModel {
    private String title;
    private String description;
    private Integer priority;
    private LocalDate creationDate;
    private String boardTitle;

    public static Function<Task, TaskOverviewModel> entityToModelMapper() {
        return task -> TaskOverviewModel.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .creationDate(task.getCreationDate())
                .boardTitle(task.getBoard().getTitle())
                .build();
    }
}
