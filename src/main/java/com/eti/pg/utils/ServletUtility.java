package com.eti.pg.utils;

import javax.servlet.http.HttpServletRequest;

public class ServletUtility {
    public static String parseRequestPath(HttpServletRequest request) {
        var path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }
}
