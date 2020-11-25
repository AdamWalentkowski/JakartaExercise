package com.eti.pg.task.view;

import com.eti.pg.board.service.BoardService;
import com.eti.pg.task.model.TaskCreationModel;
import com.eti.pg.task.service.TaskService;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
public class TaskCreation implements Serializable {

    private TaskService taskService;
    private BoardService boardService;

    @EJB
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @EJB
    public void setBoardService(BoardService boardService) {
        this.boardService = boardService;
    }

    @Setter
    @Getter
    private Long boardId;

    @Getter
    private TaskCreationModel taskCreationModel;

    public void init() {
        this.taskCreationModel = TaskCreationModel.builder()
                .board(boardService.findBoardById(boardId).orElseThrow())
                .build();
    }

    public String createTaskAction() {
        taskService.createTask(TaskCreationModel.modelToEntityMapper().apply(taskCreationModel));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
