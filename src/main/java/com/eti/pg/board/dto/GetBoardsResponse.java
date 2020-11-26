package com.eti.pg.board.dto;

import com.eti.pg.board.entity.Board;
import com.eti.pg.board.model.BoardListModel;
import com.eti.pg.user.dto.GetUserResponse;
import com.eti.pg.user.dto.GetUsersResponse;
import com.eti.pg.user.entity.User;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardsResponse {
    @Singular
    private List<GetBoardResponse> boards;

    public static Function<Collection<Board>, GetBoardsResponse> entityToDtoMapper() {
        return boards -> {
            var response = GetBoardsResponse.builder();
            boards.stream()
                    .map(board -> GetBoardResponse.builder()
                            .id(board.getId())
                            .title(board.getTitle())
                            .boardScope(board.getBoardScope())
                            .isPrivate(board.isPrivate())
                            .build())
                    .forEach(response::board);
            return response.build();
        };
    }
}
