package com.example.training.common;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.training.common.exceptions.PermissionDenyException;
import com.example.training.core.entity.User;
import com.example.training.core.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Aspect
@Component
public class PermissionAspect {
    private static final Logger logger = LoggerFactory.getLogger(PermissionAspect.class);

    @Resource
    private UserMapper userMapper;

    @Pointcut("@annotation(com.example.training.common.annotations.PermissionAccess)")
    public void checkPermission() {
    }

    @ResponseBody
    @Around("checkPermission()")
    public Object isHasPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .select(User::getUserId, User::getJobId, User::getGender,  User::getRole, User::getEmail, User::getPhone)
                        .eq(User::getUserId, request.getHeader("userId"))
        );

        if (Objects.isNull(user)) {
            throw new PermissionDenyException("权限不足");
        } else {
            request.setAttribute("user", user);
            return joinPoint.proceed();
        }

    }
}
