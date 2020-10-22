package com.eti.pg.user.servlet;

import com.eti.pg.user.entity.User;
import com.eti.pg.user.service.UserService;
import com.eti.pg.utils.ServletUtility;
import org.apache.commons.io.FileUtils;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.core.HttpHeaders;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {
        AvatarServlet.Paths.AVATARS + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)


public class AvatarServlet extends HttpServlet {
    private final UserService userService;
    private final Jsonb jsonb = JsonbBuilder.create();

    @Inject
    public AvatarServlet(UserService userService) {
        this.userService = userService;
    }

    public static class Paths {
        public static final String AVATARS = "/api/avatars";
    }

    public static class Patterns {
        public static final String AVATAR = "^/[0-9]+/?$";
    }

    public static class Parameters {
        public static final String AVATAR = "avatar";
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                getAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        if (Paths.AVATARS.equals(request.getServletPath())) {
            if (path.matches(Patterns.AVATAR)) {
                postAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                putAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                deleteAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void deleteAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = userService.findUserById(id);

        if (user.isPresent()) {
            if (user.get().getAvatarPath() != null) {
                userService.deleteAvatar(id);
            }
            else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void putAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = userService.findUserById(id);

        if (user.isPresent()) {
            Part avatar = request.getPart(Parameters.AVATAR);
            if (avatar != null) {
                userService.addAvatar(id, avatar.getInputStream());
            }
            else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void postAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {
            if (user.get().getAvatarPath() == null) {
                Part avatar = request.getPart(Parameters.AVATAR);
                if (avatar != null) {
                    userService.addAvatar(id, avatar.getInputStream());
                }
                else {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            }
            else {
                response.sendError(HttpServletResponse.SC_CONFLICT);
            }
        }
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    private void getAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = userService.findUserById(id);

        if (user.isPresent()) {
            if (user.get().getAvatarPath() != null) {
                response.addHeader(HttpHeaders.CONTENT_TYPE, "image/png");
                response.setContentLength(FileUtils.readFileToByteArray(new File(user.get().getAvatarPath())).length);
                response.getOutputStream().write(FileUtils.readFileToByteArray(new File(user.get().getAvatarPath())));
            }
            else {
                response.sendError(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
