package com.eti.pg.board.view;

import com.eti.pg.board.model.BoardOverviewModel;
import com.eti.pg.board.service.BoardService;
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

@ViewScoped
@Named
public class BoardOverview implements Serializable {
    private final BoardService boardService;
    private final TaskService taskService;
    @Getter
    private BoardOverviewModel boardOverviewModel;

    @Setter
    @Getter
    private Long id;


    @Inject
    public BoardOverview(BoardService boardService, TaskService taskService) {
        this.boardService = boardService;
        this.taskService = taskService;
    }

    public void init() throws IOException {
        var board = boardService.findBoardById(id);
        if (board.isPresent()) {
            this.boardOverviewModel = BoardOverviewModel
                    .entityToModelMapper()
                    .apply(board.get(), taskService.findTasksByBoardId(board.get().getId()));
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Board not found");
        }
    }

    public String deleteTaskAction(Long id) {
        taskService.deleteTask(id);
        var viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
