package com.eti.pg.user.controller;

import com.eti.pg.user.dto.CreateUserRequest;
import com.eti.pg.user.dto.GetUserResponse;
import com.eti.pg.user.dto.GetUsersResponse;
import com.eti.pg.user.entity.UserRole;
import com.eti.pg.user.service.UserService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/users")
@RolesAllowed({UserRole.ADMIN, UserRole.MAINTAINER, UserRole.DEVELOPER})
public class UserController {
    private UserService userService;

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserController() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        return Response.ok(GetUsersResponse.entityToDtoMapper().apply(userService.findAllUsers())).build();
    }

    @GET
    @Path("{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("login") String login) {
        var user = userService.findUserByLogin(login);
        return user.isPresent() ?
                Response.ok(GetUserResponse.entityToDtoMapper().apply(user.get())).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    @PermitAll
    public Response createUser(CreateUserRequest createUserRequest) {
        var newUser = CreateUserRequest.dtoToEntityMapper().apply(createUserRequest);
        userService.createUser(newUser);
        return Response.created(UriBuilder.fromMethod(UserController.class, "getUser")
                .build(newUser.getLogin())).build();
    }
}
