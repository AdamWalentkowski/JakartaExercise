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
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateBoardRequest {
    private String title;
    private String boardScope;
    private boolean isPrivate;

    public static Function<CreateBoardRequest, Board> dtoToEntityMapper() {
        return request -> Board.builder()
                .title(request.getTitle())
                .boardScope(BoardScope.valueOf(request.getBoardScope()))
                .isPrivate(request.isPrivate())
                .build();
    }
}
