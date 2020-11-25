package com.eti.pg.user.servlet;

import com.eti.pg.user.dto.GetUserResponse;
import com.eti.pg.user.dto.GetUsersResponse;
import com.eti.pg.user.entity.User;
import com.eti.pg.user.service.UserService;
import com.eti.pg.utils.ServletUtility;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {
        UserServlet.Paths.USERS + "/*"
})

public class UserServlet extends HttpServlet {
    private UserService userService;
    private final Jsonb jsonb = JsonbBuilder.create();

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public static class Paths {
        public static final String USERS = "/api/users";
    }

    public static class Patterns {
        public static final String USERS = "^/?$";
        public static final String USER = "^/[0-9]+/?$";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var path = ServletUtility.parseRequestPath(request);
        var servletPath = request.getServletPath();
        if (Paths.USERS.equals(servletPath)) {
            if (path.matches(Patterns.USER)) {
                getUser(request, response);
                return;
            } else if (path.matches(Patterns.USERS)) {
                getUsers(response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        var user = userService.findUserById(id);

        if (user.isPresent()) {
            response.getWriter()
                    .write(jsonb.toJson(GetUserResponse.entityToDtoMapper().apply(user.get())));
            return;
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void getUsers(HttpServletResponse response) throws IOException {
        response.getWriter()
                .write(jsonb.toJson(GetUsersResponse.entityToDtoMapper().apply(userService.findAllUsers())));
    }

}
