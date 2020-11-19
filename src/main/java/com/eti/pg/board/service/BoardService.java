package com.eti.pg.board.service;

import com.eti.pg.board.entity.Board;
import com.eti.pg.board.repository.BoardRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class BoardService {
    private BoardRepository boardRepository;

    @Inject
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    public Optional<Board> findBoardById(Long id) {
        return boardRepository.find(id);
    }

    public void deleteBoard(Long id) {
        boardRepository.delete(boardRepository.find(id).orElseThrow());
    }

    public void create(Board board) {
        boardRepository.create(board);
    }
}
