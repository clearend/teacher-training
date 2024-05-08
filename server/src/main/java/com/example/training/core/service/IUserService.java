package com.example.training.core.service;

import com.example.training.core.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.training.core.entity.request.LoginRequest;
import com.example.training.core.entity.request.UpsertUserRequest;
import com.example.training.core.entity.request.UserListRequest;
import com.example.training.core.entity.vo.LoginVO;
import com.example.training.core.entity.vo.UserInfoVO;
import com.example.training.core.entity.vo.UserListItemVO;
import com.example.training.core.entity.vo.UserListVO;

import java.util.List;

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

    UserListVO getUserList(UserListRequest userListRequest);

    UserInfoVO getUserInfo(String id);

    UserInfoVO upsertUser(UpsertUserRequest upsertUserRequest);

    UserInfoVO deleteUser(String id);
}
