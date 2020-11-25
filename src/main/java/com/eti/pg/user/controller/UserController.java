package com.eti.pg.user.controller;

import com.eti.pg.board.controller.BoardController;
import com.eti.pg.board.dto.CreateBoardRequest;
import com.eti.pg.board.dto.GetBoardResponse;
import com.eti.pg.user.dto.CreateUserRequest;
import com.eti.pg.user.dto.GetUserResponse;
import com.eti.pg.user.service.UserService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/users")
public class UserController {
    private UserService userService;

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserController() {
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") Long id) {
        var user = userService.findUserById(id);
        return user.isPresent() ?
                Response.ok(GetUserResponse.entityToDtoMapper().apply(user.get())).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBoard(CreateUserRequest createUserRequest) {
        var newUser = CreateUserRequest.dtoToEntityMapper().apply(createUserRequest);
        userService.createUser(newUser);
        return Response.created(UriBuilder.fromMethod(UserController.class, "getUser")
                .build(newUser.getId())).build();
    }


}
