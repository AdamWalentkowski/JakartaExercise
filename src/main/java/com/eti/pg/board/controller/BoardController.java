package com.eti.pg.board.controller;

import com.eti.pg.board.dto.CreateBoardRequest;
import com.eti.pg.board.dto.GetBoardResponse;
import com.eti.pg.board.dto.GetBoardsResponse;
import com.eti.pg.board.dto.UpdateBoardRequest;
import com.eti.pg.board.service.BoardService;
import com.eti.pg.serialization.CloningUtility;
import com.eti.pg.task.service.TaskService;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/boards")
public class BoardController {

    private BoardService boardService;

    private TaskService taskService;

    public BoardController() {
    }

    @Inject
    public void setBoardService(BoardService boardService) {
        this.boardService = boardService;
    }

    @Inject
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBoards() {
        return Response.ok(GetBoardsResponse.entityToDtoMapper().apply(boardService.findAllBoards())).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBoard(@PathParam("id") Long id) {
        var board = boardService.findBoardById(id);
        return board.isPresent() ?
                Response.ok(GetBoardResponse.entityToDtoMapper().apply(board.get())).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBoard(CreateBoardRequest createBoardRequest) {
        var newBoard = CreateBoardRequest.dtoToEntityMapper().apply(createBoardRequest);
        boardService.createBoard(newBoard);
        return Response.created(UriBuilder.fromMethod(BoardController.class, "getBoard")
                .build(newBoard.getId())).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBoard(@PathParam("id") Long id, UpdateBoardRequest updateBoardRequest) {
        var boardToUpdate = boardService.findBoardById(id);
        if(boardToUpdate.isPresent()) {
            boardService.updateBoard(UpdateBoardRequest.dtoToEntityUpdater().apply(updateBoardRequest, boardToUpdate.get()));
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
public Response deleteBoard(@PathParam("id") Long id) {
        var boardToDelete = boardService.findBoardById(id);
        if (boardToDelete.isPresent()) {
            var deletedBoard = GetBoardResponse.entityToDtoMapper().apply(CloningUtility.clone(boardToDelete.get()));
            taskService.findTasksByBoardId(id).forEach(task -> taskService.deleteTask(task.getId()));
            boardService.deleteBoard(id);
            return Response.ok(deletedBoard).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
