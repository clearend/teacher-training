package com.example.training.core.controller;

import com.example.training.common.ResultResponse;
import com.example.training.common.annotations.PermissionAccess;
import com.example.training.common.exceptions.BizException;
import com.example.training.core.entity.User;
import com.example.training.core.entity.request.*;
import com.example.training.core.entity.vo.SysFileVO;
import com.example.training.core.entity.vo.TrainingAuditListVO;
import com.example.training.core.service.ITrainingAuditService;
import com.example.training.core.service.ITrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
@RestController
@RequestMapping("/core/trainingAudit")
@CrossOrigin
@ControllerAdvice
@Tag(name = "培训审批控制器")
public class TrainingAuditController {
    @Resource
    private ITrainingAuditService iTrainingAuditService;

    @PermissionAccess
    @PostMapping("/examineTraining")
    @Operation(summary = "审核培训")
    public ResultResponse<String> examineTraining(@RequestBody ExamineRequest createTrainingRequest) {
        iTrainingAuditService.examineTraining(createTrainingRequest);
        return ResultResponse.success("success");
    }

    @PermissionAccess
    @PostMapping("/uploadLearnRecord")
    @Operation(summary = "上报学习记录")
    public ResultResponse<String> uploadLearnRecord(
            @RequestBody UploadLearnRecordRequest request,
            @RequestAttribute("user") User user
    ) {
        iTrainingAuditService.uploadLearnRecord(request, user);
        return ResultResponse.success("success");
    }

    @PermissionAccess
    @PostMapping("/list")
    @Operation(summary = "获取审批列表")
    public ResultResponse<TrainingAuditListVO> getTrainingAuditList(
            @RequestBody TrainingAuditListRequest trainingAuditListRequest,
            @RequestAttribute("user") User user
    ) {
        return ResultResponse.success(iTrainingAuditService.getTrainingAuditList(trainingAuditListRequest, user));
    }

    @PermissionAccess
    @PostMapping("/cancel")
    @Operation(summary = "撤销上报")
    public ResultResponse<?> cancelRecord(
            @RequestBody SingleIdRequest singleIdRequest,
            @RequestAttribute("user") User user
    ) {
        iTrainingAuditService.cancelRecord(singleIdRequest, user);
        return ResultResponse.success();
    }

    @PermissionAccess
    @PostMapping("/info")
    @Operation(summary = "获取培训详情")
    public ResultResponse<?> getAuditInfo(
            @RequestBody SingleIdRequest singleIdRequest,
            @RequestAttribute("user") User user
    ) {

        return ResultResponse.success(iTrainingAuditService.getAuditInfo(singleIdRequest, user));
    }
}
