package com.example.training.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.training.common.exceptions.BizException;
import com.example.training.core.entity.User;
import com.example.training.core.entity.enums.RoleEnum;
import com.example.training.core.entity.request.LoginRequest;
import com.example.training.core.entity.request.UserListRequest;
import com.example.training.core.entity.vo.LoginVO;
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
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>();
        Integer currentPage = userListRequest.getPageRequest().getCurrentPage();
        Integer pageSize = userListRequest.getPageRequest().getPageSize();

        Long count = userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getRole, RoleEnum.USER));

        wrapper.select(User::getUserId, User::getUserName, User::getJobId, User::getGender, User::getEmail, User::getPhone, User::getRole)
                .eq(User::getRole, RoleEnum.USER)
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
}
