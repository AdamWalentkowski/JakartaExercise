package com.eti.pg.task.dto;

import com.eti.pg.task.entity.Task;
import lombok.*;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetTaskResponse {
    private Long id;
    private String title;
    private String description;
    private Integer priority;
    private LocalDate creationDate;

    public static Function<Task, GetTaskResponse> entityToDtoMapper() {
        return task -> GetTaskResponse.builder()
                            .id(task.getId())
                            .title(task.getTitle())
                            .description(task.getDescription())
                            .priority(task.getPriority())
                            .creationDate(task.getCreationDate())
                            .build();
    }
}
