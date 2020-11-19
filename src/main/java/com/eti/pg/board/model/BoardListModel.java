package com.eti.pg.board.model;

import com.eti.pg.board.entity.Board;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class BoardListModel {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class BoardSummary {
        private Long id;
        private String title;
    }

    @Singular
    List<BoardSummary> boards;

    public static Function<Collection<Board>, BoardListModel> entityToModelMapper() {
        return boards -> {
            var modelBuilder = BoardListModel.builder();
            boards.stream()
                    .map(board -> BoardSummary.builder()
                                    .id(board.getId())
                                    .title(board.getTitle())
                                    .build())
                    .forEach(modelBuilder::board);
            return modelBuilder.build();
        };
    }
}
