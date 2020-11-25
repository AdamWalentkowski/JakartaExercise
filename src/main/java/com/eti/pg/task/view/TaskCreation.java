package com.eti.pg.task.view;

import com.eti.pg.board.service.BoardService;
import com.eti.pg.task.model.TaskCreationModel;
import com.eti.pg.task.service.TaskService;
import lombok.Getter;
import lombok.Setter;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
public class TaskCreation implements Serializable {

    private final TaskService taskService;
    private final BoardService boardService;

    @Setter
    @Getter
    private Long boardId;

    @Getter
    private TaskCreationModel taskCreationModel;

    @Inject
    public TaskCreation(TaskService taskService, BoardService boardService) {
        this.taskService = taskService;
        this.boardService = boardService;
    }

    public void init() {
        this.taskCreationModel = TaskCreationModel.builder()
                .board(boardService.findBoardById(boardId).orElseThrow())
                .build();
    }

    public String createTaskAction() {
        taskService.create(TaskCreationModel.modelToEntityMapper().apply(taskCreationModel));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
