package com.eti.pg.task.view;

import com.eti.pg.board.entity.Board;
import com.eti.pg.board.service.BoardService;
import com.eti.pg.task.entity.Task;
import com.eti.pg.task.model.TaskEditModel;
import com.eti.pg.task.service.TaskService;
import lombok.Getter;
import lombok.Setter;


import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@ViewScoped
@Named
public class TaskEdit implements Serializable {

    private final TaskService taskService;
    private final BoardService boardService;

    @Setter
    @Getter
    private Long id;

    @Getter
    private TaskEditModel taskEditModel;
    @Getter
    private List<Board> availableBoards;

    @Inject
    public TaskEdit(TaskService taskService, BoardService boardService) {
        this.taskService = taskService;
        this.boardService = boardService;
    }

    public void init() throws IOException {
        Optional<Task> task = taskService.findTaskById(id);
        if (task.isPresent()) {
            this.taskEditModel = TaskEditModel.entityToModelMapper().apply(task.get());
            this.availableBoards = boardService.findAllBoards();
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Task not found");
        }
    }

    public String saveChangesAction() {
        taskService.update(TaskEditModel.modelToEntityUpdater().apply(taskEditModel, taskService.findTaskById(id).orElseThrow()));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

    public Board getBoard(long id) {
        return boardService.findBoardById(id).orElseThrow();
    }
}
