package com.eti.pg.board.service;

import com.eti.pg.board.entity.Board;
import com.eti.pg.board.repository.BoardRepository;
import lombok.NoArgsConstructor;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
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

    public void createBoard(Board board) {
        boardRepository.create(board);
    }

    public void deleteBoard(Long id) {
        boardRepository.delete(boardRepository.find(id).orElseThrow());
    }

    public void updateBoard(Board board) {
        boardRepository.update(board);
    }
}
