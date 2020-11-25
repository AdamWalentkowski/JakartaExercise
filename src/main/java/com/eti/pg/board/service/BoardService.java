package com.eti.pg.board.service;

import com.eti.pg.board.entity.Board;
import com.eti.pg.board.repository.BoardRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
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

    public Optional<Board> findBoardByName(String boardName) {
        return boardRepository.find(boardName);
    }

    @Transactional
    public void createBoard(Board board) {
        boardRepository.create(board);
    }

    @Transactional
    public void deleteBoard(Long id) {
        boardRepository.delete(boardRepository.find(id).orElseThrow());
    }

    @Transactional
    public void updateBoard(Board board) {
        boardRepository.update(board);
    }
}
