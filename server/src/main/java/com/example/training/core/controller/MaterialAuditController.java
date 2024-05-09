package com.example.training.core.controller;

import com.example.training.common.ResultResponse;
import com.example.training.common.annotations.PermissionAccess;
import com.example.training.core.entity.User;
import com.example.training.core.entity.request.MaterialAuditListRequest;
import com.example.training.core.entity.request.MaterialAuditRequest;
import com.example.training.core.entity.vo.MaterialAuditListVO;
import com.example.training.core.service.IMaterialAuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
@RestController
@RequestMapping("/core/materialAudit")
@CrossOrigin
@ControllerAdvice
@Tag(name = "素材审核控制器")
public class MaterialAuditController {

    @Resource
    private IMaterialAuditService materialAuditService;

    @PermissionAccess
    @PostMapping("/list")
    @Operation(summary = "获取素材审批列表")
    public ResultResponse<MaterialAuditListVO> getMaterialAuditList(@RequestBody MaterialAuditListRequest materialAuditListRequest) {
        return ResultResponse.success(materialAuditService.getMaterialAuditList(materialAuditListRequest));
    }

    @PermissionAccess
    @PostMapping("/audit")
    @Operation(summary = "素材审核")
    public ResultResponse<?> auditMaterial(
            @RequestBody MaterialAuditRequest materialAuditRequest,
            @RequestAttribute("user") User user
    ) {
        materialAuditService.auditMaterial(materialAuditRequest);
        return ResultResponse.success();
    }

}
