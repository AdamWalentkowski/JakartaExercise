package com.eti.pg.user.servlet;

import com.eti.pg.user.service.UserService;
import com.eti.pg.utils.ServletUtility;
import org.apache.commons.io.FileUtils;

import javax.ejb.EJB;
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

@WebServlet(urlPatterns = {
        AvatarServlet.Paths.AVATARS + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)


public class AvatarServlet extends HttpServlet {
    private UserService userService;

    @EJB
    public void setUserService(UserService userService) {
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
        var path = ServletUtility.parseRequestPath(request);
        var servletPath = request.getServletPath();
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
        var path = ServletUtility.parseRequestPath(request);
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
        var path = ServletUtility.parseRequestPath(request);
        var servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                putAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var path = ServletUtility.parseRequestPath(request);
        var servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                deleteAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void getAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var login = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        var user = userService.findUserByLogin(login);

        if (user.isPresent()) {
            if (user.get().getAvatarPath() != null) {
                response.addHeader(HttpHeaders.CONTENT_TYPE, "image/png");
                response.setContentLength(FileUtils.readFileToByteArray(new File(user.get().getAvatarPath())).length);
                response.getOutputStream().write(FileUtils.readFileToByteArray(new File(user.get().getAvatarPath())));
                return;
            }
            response.sendError(HttpServletResponse.SC_NO_CONTENT);
            return;
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void postAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var login = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        var user = userService.findUserByLogin(login);
        if (user.isPresent()) {
            if (user.get().getAvatarPath() == null) {
                var avatar = request.getPart(Parameters.AVATAR);
                if (avatar != null) {
                    userService.addAvatar(login, avatar.getInputStream());
                    return;
                }
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                return;
            }
            response.sendError(HttpServletResponse.SC_CONFLICT);
            return;
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void putAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var login = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        var user = userService.findUserByLogin(login);

        if (user.isPresent()) {
            var avatar = request.getPart(Parameters.AVATAR);
            if (avatar != null) {
                userService.addAvatar(login, avatar.getInputStream());
                return;
            }
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void deleteAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var login = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        var user = userService.findUserByLogin(login);

        if (user.isPresent()) {
            if (user.get().getAvatarPath() != null) {
                userService.deleteAvatar(login);
                return;
            }
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
