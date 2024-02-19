package com.project.messageapp.utils;

public class RequestContext {
    private static final ThreadLocal<String> usernameThreadLocal = new ThreadLocal<>();

    public static String getUsername() {
        return usernameThreadLocal.get();
    }

    public static void setUsername(String username) {
        usernameThreadLocal.set(username);
    }

    public static void clear() {
        usernameThreadLocal.remove();
    }
}