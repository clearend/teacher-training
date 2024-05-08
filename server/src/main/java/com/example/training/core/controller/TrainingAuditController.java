package com.example.training.core.controller;

import com.example.training.common.ResultResponse;
import com.example.training.common.annotations.PermissionAccess;
import com.example.training.common.exceptions.BizException;
import com.example.training.core.entity.request.CreateTrainingRequest;
import com.example.training.core.entity.request.ExamineRequest;
import com.example.training.core.entity.request.UploadLearnRecordRequest;
import com.example.training.core.entity.vo.SysFileVO;
import com.example.training.core.service.ITrainingAuditService;
import com.example.training.core.service.ITrainingService;
import io.swagger.v3.oas.annotations.Operation;
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
    public ResultResponse<String> uploadLearnRecord(@RequestBody UploadLearnRecordRequest request) {
        iTrainingAuditService.uploadLearnRecord(request);
        return ResultResponse.success("success");
    }
}
