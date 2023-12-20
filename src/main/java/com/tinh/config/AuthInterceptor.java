package com.tinh.config;

import com.tinh.model.dto.user.UserLoginDTO;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession();
//        UserLoginDTO admin = (UserLoginDTO) session.getAttribute("userLoginAdmin");
//        if (admin != null) {
//            return true;
//        }
//        response.sendRedirect("/signin");
//        return false;
//    }
}
