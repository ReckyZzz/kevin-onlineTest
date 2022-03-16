package com.kevin.onlinetest.util;

import javax.servlet.http.HttpSession;

/**
 * @author herokilito
 */
public class SessionUtil {

    private static final ThreadLocal<HttpSession> THREAD_LOCAL = new ThreadLocal<>();

    public static void setSession(HttpSession session) {
        THREAD_LOCAL.set(session);
    }

    public static HttpSession getSession() {
        return THREAD_LOCAL.get();
    }

    public static void removeSession() {
        THREAD_LOCAL.remove();
    }
}
