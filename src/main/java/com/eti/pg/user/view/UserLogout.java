package com.eti.pg.user.view;

import lombok.SneakyThrows;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
@Named
public class UserLogout {
    private HttpServletRequest request;

    @Inject
    public UserLogout(HttpServletRequest request) {
        this.request = request;
    }

    @SneakyThrows
    public String logoutAction() {
        request.logout();
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}
