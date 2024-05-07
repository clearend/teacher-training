package com.example.training.core.controller;

import com.example.training.common.ResultResponse;
import com.example.training.common.annotations.PermissionAccess;
import com.example.training.core.entity.request.CreateTrainingRequest;
import com.example.training.core.entity.request.ExamineRequest;
import com.example.training.core.service.ITrainingAuditService;
import com.example.training.core.service.ITrainingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
@Controller
@RequestMapping("/core/trainingAudit")
public class TrainingAuditController {
    @Resource
    private ITrainingAuditService iTrainingAuditService;

    @PermissionAccess
    @PostMapping("/examineTraining")
    @Operation(summary = "审核培训")
    public ResultResponse examineTraining(@RequestBody ExamineRequest createTrainingRequest) {
        iTrainingAuditService.examineTraining(createTrainingRequest);
        return ResultResponse.success("success");
    }
}
