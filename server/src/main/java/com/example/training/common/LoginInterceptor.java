package com.example.training.common;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.training.common.annotations.PermissionAccess;
import com.example.training.core.entity.User;
import com.example.training.core.mapper.UserMapper;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Objects;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        //获取方法处理器
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.getMethodAnnotation(PermissionAccess.class) == null) {
            return true;
        }

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId, request.getHeader("userId")));

        if (Objects.isNull(user)) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            try {
                response.getWriter().write("{\"code\":\"403\",\"msg\":\"权限不足\"}");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        request.setAttribute("user", user);

        return true;
    }

}
