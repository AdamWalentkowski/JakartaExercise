package com.eti.pg.task.dto;

import com.eti.pg.task.entity.Task;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class GetTasksResponse {
    @Singular
    private List<GetTaskResponse> tasks;

    public static Function<Collection<Task>, GetTasksResponse> entityToDtoMapper() {
        return tasks -> {
            var response = GetTasksResponse.builder();
            tasks.stream()
                    .map(task -> GetTaskResponse.builder()
                            .id(task.getId())
                            .title(task.getTitle())
                            .description(task.getDescription())
                            .priority(task.getPriority())
                            .creationDate(task.getCreationDate())
                            .build())
                    .forEach(response::task);
            return response.build();
        };
    }
}
