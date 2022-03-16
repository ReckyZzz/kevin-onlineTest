package com.kevin.onlinetest.interceptor;

import cn.hutool.json.JSONUtil;
import com.kevin.onlinetest.comm.CommonConstants;
import com.kevin.onlinetest.response.CommonResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author herokilito
 */
public class AdminAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute(CommonConstants.ADMIN_SESSION_NAME) == null) {
            setReturn(response);
            return false;
        }
        return true;
    }

    /**
     * 返回错误的信息
     * @param response 响应
     * @throws IOException 异常
     */
    private static void setReturn(HttpServletResponse response) throws IOException {
        //UTF-8编码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        CommonResponse<String> build =
                new CommonResponse<>(100, "登录信息失效，请重新登录", null);
        String json = JSONUtil.toJsonStr(build);
        response.getWriter().print(json);
    }

}
