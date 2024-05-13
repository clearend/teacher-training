package com.example.training.core.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.training.common.exceptions.BizException;
import com.example.training.core.entity.User;
import com.example.training.core.entity.enums.RoleEnum;
import com.example.training.core.entity.request.LoginRequest;
import com.example.training.core.entity.request.UpsertUserRequest;
import com.example.training.core.entity.request.UserListRequest;
import com.example.training.core.entity.vo.LoginVO;
import com.example.training.core.entity.vo.UserInfoVO;
import com.example.training.core.entity.vo.UserListItemVO;
import com.example.training.core.entity.vo.UserListVO;
import com.example.training.core.mapper.UserMapper;
import com.example.training.core.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public LoginVO login(LoginRequest loginRequest) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>();
        wrapper.select(User::getUserId, User::getJobId, User::getGender, User::getEmail, User::getPhone, User::getRole)
                .eq(User::getJobId, loginRequest.getUsername())
                .eq(User::getPassword, loginRequest.getPassword());
        User user = userMapper.selectOne(wrapper);

        if (Objects.isNull(user)) {
            throw new BizException("用户不存在");
        }

        LoginVO loginVO = new LoginVO();
        BeanUtils.copyProperties(user, loginVO);
        return loginVO;
    }

    @Override
    public UserListVO getUserList(UserListRequest userListRequest) {
        Integer currentPage = userListRequest.getPageRequest().getCurrentPage();
        Integer pageSize = userListRequest.getPageRequest().getPageSize();

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>();
        wrapper.eq(User::getRole, RoleEnum.USER);

        if (userListRequest.getJobId() != null && !userListRequest.getJobId().isEmpty()) {
            wrapper.like(User::getJobId, userListRequest.getJobId());
        }
        if (userListRequest.getUserName() != null && !userListRequest.getUserName().isEmpty()) {
            wrapper.like(User::getUserName, userListRequest.getUserName());
        }

        Long count = userMapper.selectCount(wrapper);

        wrapper.select(User::getUserId, User::getUserName, User::getJobId, User::getGender, User::getEmail, User::getPhone, User::getRole)
                .last("limit " + pageSize + " offset " + ((currentPage - 1) * pageSize));
        List<User> userList = userMapper.selectList(wrapper);

        UserListVO userListVO = new UserListVO();
        userListVO.setCount(count);
        userListVO.setUserList(userList.stream().map(user -> {
            UserListItemVO userListItemVO = new UserListItemVO();
            BeanUtils.copyProperties(user, userListItemVO);
            return userListItemVO;
        }).collect(Collectors.toList()));

        return userListVO;
    }

    @Override
    public UserInfoVO getUserInfo(String id) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId, id));
        if (Objects.isNull(user)) {
            throw new BizException("用户不存在");
        }

        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfoVO);
        return userInfoVO;
    }

    @Override
    public UserInfoVO upsertUser(UpsertUserRequest upsertUserRequest) {
        User user = new User();
        BeanUtils.copyProperties(upsertUserRequest, user);
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(user.getJobId());
        }

        UserInfoVO userInfoVO = new UserInfoVO();
        if (user.getUserId() == null || user.getUserId().isEmpty()) {
            user.setUserId("u-" + IdUtil.fastSimpleUUID());
            user.setRole(RoleEnum.USER);

            userMapper.insert(user);
        } else {
            Long count = userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getUserId, user.getUserId()));
            if (count <= 0) {
                throw new BizException("用户不存在");
            }

            userMapper.update(user, new LambdaUpdateWrapper<User>().eq(User::getUserId, user.getUserId()));
        }

        BeanUtils.copyProperties(user, userInfoVO);
        return userInfoVO;
    }

    @Override
    public UserInfoVO deleteUser(String id) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId, id));
        if (Objects.isNull(user)) {
            throw new BizException("待删除用户不存在");
        }
        userMapper.deleteById(user.getId());

        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfoVO);
        return userInfoVO;
    }
}
