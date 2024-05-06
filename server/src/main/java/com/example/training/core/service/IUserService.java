package com.example.training.core.service;

import com.example.training.core.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.training.core.entity.request.LoginRequest;
import com.example.training.core.entity.vo.LoginVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
public interface IUserService extends IService<User> {

    LoginVO login(LoginRequest loginRequest);
}
