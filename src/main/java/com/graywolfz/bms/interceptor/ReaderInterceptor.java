package com.graywolfz.bms.interceptor;

import com.graywolfz.bms.model.User;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 读者拦截器：拦截读者不能访问的请求
public class ReaderInterceptor implements HandlerInterceptor {

    /**
     * 截读者不能访问的请求
     *
     * @param request  请求
     * @param response 请求消息
     * @param  handler  请求处理
     * @return boolean
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("userObj");

        // 根据链式拦截关系，到这个拦截器肯定是登录过的。
        if (user.getIsadmin() == 0) {    //如果是用户，则拦截
            System.out.println("读者不能进管理员界面!");
            // 重定向到登录界面
            response.sendRedirect(request.getContextPath() + "/index.html");
            return false;
        }
        return true;    //放行
    }
}
