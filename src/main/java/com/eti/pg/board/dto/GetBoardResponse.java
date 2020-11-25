package com.eti.pg.board.dto;

import com.eti.pg.board.BoardScope;
import com.eti.pg.board.entity.Board;
import lombok.*;

import java.util.Collection;
import java.util.function.Function;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class GetBoardResponse {
    private Long id;
    private String title;
    private BoardScope boardScope;
    private boolean isPrivate;

    public static Function<Board, GetBoardResponse> entityToDtoMapper() {
        return board -> GetBoardResponse.builder()
                            .id(board.getId())
                            .title(board.getTitle())
                            .boardScope(board.getBoardScope())
                            .isPrivate(board.isPrivate())
                            .build();
    }
}
