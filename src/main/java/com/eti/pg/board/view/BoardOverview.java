package com.eti.pg.board.view;

import com.eti.pg.board.model.BoardOverviewModel;
import com.eti.pg.board.service.BoardService;
import com.eti.pg.task.service.TaskService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestScoped
@Named
public class BoardOverview {
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

    public String deleteTaskAction(Long id) throws IOException {
        taskService.deleteTask(id);
//        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
//        FacesContext.getCurrentInstance().getExternalContext().redirect("board_overview?id=" + this.id);
        return "board_list?faces-redirect=true";
    }
}
