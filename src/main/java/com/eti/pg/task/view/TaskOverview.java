package com.eti.pg.task.view;

import com.eti.pg.task.model.TaskOverviewModel;
import com.eti.pg.task.service.TaskService;
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
public class TaskOverview implements Serializable {
    private final TaskService taskService;

    @Getter
    private TaskOverviewModel taskOverviewModel;

    @Setter
    @Getter
    private Long id;


    @Inject
    public TaskOverview(TaskService taskService) {
        this.taskService = taskService;
    }

    public void init() throws IOException {
        var task = taskService.findTaskById(id);
        if (task.isPresent()) {
            this.taskOverviewModel = TaskOverviewModel
                    .entityToModelMapper()
                    .apply(task.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Task not found");
        }
    }
}
