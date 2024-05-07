package com.example.training.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.training.common.exceptions.BizException;
import com.example.training.core.entity.User;
import com.example.training.core.entity.request.LoginRequest;
import com.example.training.core.entity.vo.LoginVO;
import com.example.training.core.mapper.UserMapper;
import com.example.training.core.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
}
