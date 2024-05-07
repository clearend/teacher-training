package com.example.training.core.controller;

import com.example.training.common.ResultResponse;
import com.example.training.common.annotations.PermissionAccess;
import com.example.training.core.entity.request.LoginRequest;
import com.example.training.core.entity.request.UserListRequest;
import com.example.training.core.entity.vo.UserListItemVO;
import com.example.training.core.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
@RestController
@ControllerAdvice
@RequestMapping("/core/user")
@CrossOrigin
@Tag(name = "用户控制器")
public class UserController {
    @Resource
    private IUserService userService;

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public ResultResponse<?> login(
            @RequestBody LoginRequest loginRequest
    ) {
        return ResultResponse.success(userService.login(loginRequest));
    }

    @PermissionAccess
    @PostMapping("/list")
    @Operation(summary = "用户列表")
    public ResultResponse<List<UserListItemVO>> list(@RequestBody UserListRequest userListRequest) {
        return ResultResponse.success(userService.getUserList(userListRequest));
    }
}
