package com.eti.pg.board.view;

import com.eti.pg.board.model.BoardListModel;
import com.eti.pg.board.service.BoardService;
import com.eti.pg.task.service.TaskService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named
public class BoardList {
    private  BoardService boardService;
    private  TaskService taskService;
    private BoardListModel boardListModel;

    @EJB
    public void setBoardService(BoardService boardService) {
        this.boardService = boardService;
    }

    @EJB
    public void setTaskService(TaskService taskService) {
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
