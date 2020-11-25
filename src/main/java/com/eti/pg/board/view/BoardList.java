package com.eti.pg.board.view;

import com.eti.pg.board.model.BoardListModel;
import com.eti.pg.board.service.BoardService;
import com.eti.pg.task.service.TaskService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
public class BoardList {
    private final BoardService boardService;
    private final TaskService taskService;
    private BoardListModel boardListModel;

    @Inject
    public BoardList(BoardService boardService, TaskService taskService) {
        this.boardService = boardService;
        this.taskService = taskService;
    }

    public BoardListModel getBoardList() {
        if (boardListModel == null) {
            boardListModel = BoardListModel.entityToModelMapper().apply(boardService.findAllBoards());
        }
        return boardListModel;
    }

    public String deleteBoardAction(Long id) {
        taskService.findTasksByBoardId(id).forEach(task -> taskService.deleteTask(task.getId()));
        boardService.deleteBoard(id);
        return "board_list?faces-redirect=true";
    }
}
