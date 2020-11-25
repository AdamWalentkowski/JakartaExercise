package com.eti.pg.task.controller;

import com.eti.pg.board.service.BoardService;
import com.eti.pg.serialization.CloningUtility;
import com.eti.pg.task.dto.CreateTaskRequest;
import com.eti.pg.task.dto.GetTaskResponse;
import com.eti.pg.task.dto.GetTasksResponse;
import com.eti.pg.task.dto.UpdateTaskRequest;
import com.eti.pg.task.service.TaskService;


import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/boards/{boardName}/tasks")
public class TaskController {

    private BoardService boardService;

    private TaskService taskService;

    public TaskController() {
    }

    @EJB
    public void setBoardService(BoardService boardService) {
        this.boardService = boardService;
    }

    @EJB
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasks(@PathParam("boardName") String boardName) {
        return Response.ok(GetTasksResponse.entityToDtoMapper().apply(taskService.findTasksByBoardName(boardName))).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTask(@PathParam("boardName") String boardName, @PathParam("id") Long id) {
        var task = taskService.findTaskByIdAndBoardName(id, boardName);
        return task.isPresent() ?
                Response.ok(GetTaskResponse.entityToDtoMapper().apply(task.get())).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response CreateTask(@PathParam("boardName") String boardName, CreateTaskRequest createTaskRequest) {
        var board = boardService.findBoardByName(boardName);
        if (board.isPresent()) {
            var newTask = CreateTaskRequest.dtoToEntityMapper().apply(createTaskRequest, board.get());
            taskService.createTask(newTask);
            return Response.created(UriBuilder.fromMethod(TaskController.class, "getTask")
                    .build(newTask.getId())).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTask(@PathParam("boardName") String boardName, @PathParam("id") Long id, UpdateTaskRequest updateTaskRequest) {
        var taskToUpdate = taskService.findTaskById(id);
        if(taskToUpdate.isPresent()) {
            taskService.updateTask(UpdateTaskRequest.dtoToEntityUpdater().apply(updateTaskRequest, taskToUpdate.get()));
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTask(@PathParam("boardName") String boardName, @PathParam("id") Long id) {
        var taskToDelete = taskService.findTaskById(id);
        if (taskToDelete.isPresent()) {
            var deletedTask = GetTaskResponse.entityToDtoMapper().apply(CloningUtility.clone(taskToDelete.get()));
            taskService.deleteTask(id);
            return Response.ok(deletedTask).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}

