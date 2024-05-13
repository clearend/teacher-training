package com.example.training.core.controller;

import com.example.training.common.ResultResponse;
import com.example.training.common.annotations.PermissionAccess;
import com.example.training.core.entity.User;
import com.example.training.core.entity.request.AddRemindRequest;
import com.example.training.core.entity.request.RemindMessageListRequest;
import com.example.training.core.entity.request.SingleIdRequest;
import com.example.training.core.entity.vo.RemindMessageListVO;
import com.example.training.core.service.IRemindMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author coder
 * @since 2024-05-13
 */
@RestController
@CrossOrigin
@ControllerAdvice
@RequestMapping("/core/remindMessage")
@Tag(name = "提醒消息控制器")
public class RemindMessageController {

    @Resource
    private IRemindMessageService remindMessageService;

    @PermissionAccess
    @PostMapping("/add")
    @Operation(summary = "添加提醒消息")
    public ResultResponse<?> addRemindMessage(
            @RequestBody AddRemindRequest request
    ) {
        remindMessageService.addRemindMessage(request);
        return ResultResponse.success();
    }

    @PermissionAccess
    @PostMapping("/delete")
    @Operation(summary = "删除提醒消息")
    public ResultResponse<?> deleteRemindMessage(
            @RequestBody SingleIdRequest request
    ) {
        remindMessageService.deleteRemindMessage(request.getId());
        return ResultResponse.success();
    }

    @PermissionAccess
    @PostMapping("/list")
    @Operation(summary = "获取提醒消息列表")
    public ResultResponse<RemindMessageListVO> getRemindMessageList(
            @RequestBody RemindMessageListRequest request,
            @RequestAttribute("user") User user
    ) {
        return ResultResponse.success(remindMessageService.getRemindMessageList(request, user));
    }
}
