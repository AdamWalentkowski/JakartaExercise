package com.eti.pg.board.dto;

import com.eti.pg.board.BoardScope;
import com.eti.pg.board.entity.Board;
import lombok.*;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateBoardRequest {
    private String title;
    private BoardScope boardScope;
    private boolean isPrivate;

    public static BiFunction<UpdateBoardRequest, Board, Board> dtoToEntityUpdater() {
        return (request, boardToUpdate) -> {
            boardToUpdate.setTitle(request.getTitle());
            boardToUpdate.setBoardScope(request.getBoardScope());
            boardToUpdate.setPrivate(request.isPrivate());
            return boardToUpdate;
        };
    }
}
