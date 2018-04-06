package com.common.framework.helper;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.framework.base.LoginUser;

public class RequestHolder {

    private static final ThreadLocal<LoginUser> userHolder = new ThreadLocal<LoginUser>();

    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();
    
    private static final ThreadLocal<HttpServletResponse> responseHolder = new ThreadLocal<HttpServletResponse>();

    public static void add(LoginUser sysUser) {
        userHolder.set(sysUser);
    }

    public static void add(HttpServletRequest request) {
        requestHolder.set(request);
    }
    
    public static void add(HttpServletResponse response) {
        responseHolder.set(response);
    }

    public static LoginUser getCurrentUser() {
        return userHolder.get();
    }

    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }
    
    public static HttpServletResponse getCurrentResponse() {
        return responseHolder.get();
    }

    public static void remove() {
        userHolder.remove();
        requestHolder.remove();
        responseHolder.remove();
    }
}
