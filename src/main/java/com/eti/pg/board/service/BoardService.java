package com.eti.pg.board.service;

import com.eti.pg.board.entity.Board;
import com.eti.pg.board.repository.BoardRepository;
import com.eti.pg.user.entity.UserRole;
import lombok.NoArgsConstructor;

import javax.annotation.security.RolesAllowed;
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

    @RolesAllowed({UserRole.ADMIN, UserRole.MAINTAINER, UserRole.DEVELOPER})
    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    @RolesAllowed({UserRole.ADMIN, UserRole.MAINTAINER, UserRole.DEVELOPER})
    public Optional<Board> findBoardById(Long id) {
        return boardRepository.find(id);
    }

    @RolesAllowed({UserRole.ADMIN, UserRole.MAINTAINER, UserRole.DEVELOPER})
    public Optional<Board> findBoardByName(String boardName) {
        return boardRepository.find(boardName);
    }

    @RolesAllowed(UserRole.ADMIN)
    public void createBoard(Board board) {
        boardRepository.create(board);
    }

    @RolesAllowed(UserRole.ADMIN)
    public void deleteBoard(Long id) {
        boardRepository.delete(boardRepository.find(id).orElseThrow());
    }

    @RolesAllowed({UserRole.ADMIN, UserRole.MAINTAINER, UserRole.DEVELOPER})
    public void updateBoard(Board board) {
        boardRepository.update(board);
    }

    public void flushData() {
        boardRepository.flushData();
    }
}
