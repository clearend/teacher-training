package com.example.training.core.controller;

import com.example.training.common.ResultResponse;
import com.example.training.common.annotations.PermissionAccess;
import com.example.training.core.entity.User;
import com.example.training.core.entity.request.LoginRequest;
import com.example.training.core.entity.request.SingleIdRequest;
import com.example.training.core.entity.request.UserListRequest;
import com.example.training.core.entity.request.UpsertUserRequest;
import com.example.training.core.entity.vo.UserInfoVO;
import com.example.training.core.entity.vo.UserListVO;
import com.example.training.core.service.IUserService;
import com.example.training.utils.MailUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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

    @Resource
    private MailUtils mailUtils;

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
    public ResultResponse<UserListVO> list(@RequestBody UserListRequest userListRequest) {
        return ResultResponse.success(userService.getUserList(userListRequest));
    }

    @PermissionAccess
    @PostMapping("/info")
    @Operation(summary = "用户详情")
    public ResultResponse<UserInfoVO> info(
            @RequestBody SingleIdRequest request,
            @RequestAttribute("user") User user
    ) {
        String id;
        if (Objects.nonNull(request) && !"".equals(request.getId())) {
            id = request.getId();
        } else {
            id = user.getUserId();
        }

        return ResultResponse.success(userService.getUserInfo(id));
    }

    @PermissionAccess
    @PostMapping("/upsert")
    @Operation(summary = "用户更新或插入")
    public ResultResponse<UserInfoVO> upsertUser(@RequestBody UpsertUserRequest upsertUserRequest) {
        return ResultResponse.success(userService.upsertUser(upsertUserRequest));
    }

    @PermissionAccess
    @PostMapping("/delete")
    @Operation(summary = "用户删除")
    public ResultResponse<UserInfoVO> deleteUser(@RequestBody SingleIdRequest request) {
        return ResultResponse.success(userService.deleteUser(request.getId()));
    }

    @PermissionAccess
    @PostMapping("/mail")
    @Operation(summary = "发送邮件")
    public ResultResponse<?> sendMail(
            @RequestParam(name = "to") String to,
            @RequestParam(name = "subject") String subject,
            @RequestParam(name = "content") String content
    ) {
        mailUtils.sendTextMailMessage(to, subject, content);
        return ResultResponse.success();
    }
}
