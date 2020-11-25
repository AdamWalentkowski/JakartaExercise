package com.eti.pg.board.dto;

import com.eti.pg.board.BoardScope;
import com.eti.pg.board.entity.Board;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class CreateBoardRequest {
    private String title;
    private BoardScope boardScope;
    private boolean isPrivate;

    public static Function<CreateBoardRequest, Board> dtoToEntityMapper() {
        return request -> Board.builder()
                .title(request.getTitle())
                .boardScope(request.getBoardScope())
                .isPrivate(request.isPrivate())
                .build();
    }
}
