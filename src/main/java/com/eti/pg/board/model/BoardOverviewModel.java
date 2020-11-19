package com.eti.pg.board.model;

import com.eti.pg.board.entity.Board;
import com.eti.pg.task.entity.Task;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class BoardOverviewModel {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class BoardDetails {
        private String title;
        private String boardScopeName;
        private String isPrivateAnswer;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class TaskSummary {
        private Long id;
        private String title;
    }

    private BoardDetails boardDetails;
    @Singular
    private List<TaskSummary> tasks;

    public static BiFunction<Board, Collection<Task>, BoardOverviewModel> entityToModelMapper() {
        return (board, tasks) -> {
            var modelBuilder = BoardOverviewModel.builder();
            modelBuilder.boardDetails(BoardDetails.builder()
                    .title(board.getTitle())
                    .boardScopeName(board.getBoardScope().name())
                    .isPrivateAnswer(board.isPrivate() ? "Tak" : "Nie")
                    .build());

            tasks.stream()
                    .map(task -> BoardOverviewModel.TaskSummary.builder()
                            .id(task.getId())
                            .title(task.getTitle())
                            .build())
                    .forEach(modelBuilder::task);
            return modelBuilder.build();
        };
    }
}
