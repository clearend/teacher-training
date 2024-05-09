package com.example.training.core.controller;

import com.example.training.common.ResultResponse;
import com.example.training.common.annotations.PermissionAccess;
import com.example.training.core.entity.User;
import com.example.training.core.entity.request.MaterialListRequest;
import com.example.training.core.entity.request.SingleIdRequest;
import com.example.training.core.entity.vo.MaterialListItemVO;
import com.example.training.core.entity.request.UploadMaterialRequest;
import com.example.training.core.entity.vo.MaterialListVO;
import com.example.training.core.service.IMaterialService;
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
@RequestMapping("/core/material")
@CrossOrigin
@ControllerAdvice
@Tag(name = "素材相关控制器")
public class MaterialController {

    @Resource
    private IMaterialService iMaterialService;

    @PermissionAccess
    @PostMapping("/list")
    @Operation(summary = "查看素材列表")
    public ResultResponse<MaterialListVO> findMaterialList(@RequestBody MaterialListRequest materialListRequest) {
        return ResultResponse.success(iMaterialService.findMaterialList(materialListRequest));
    }

    @PermissionAccess
    @PostMapping("/delete")
    @Operation(summary = "删除素材")
    public ResultResponse<?> deleteMaterial(@RequestBody SingleIdRequest singleIdRequest){
        iMaterialService.deleteMaterial(singleIdRequest);
        return ResultResponse.success();
    }

    @PermissionAccess
    @PostMapping("/upload")
    @Operation(summary = "上传素材")
    public ResultResponse<?> uploadMaterial(
            @RequestBody UploadMaterialRequest uploadMaterialRequest,
            @RequestAttribute("user") User user
    ){
        iMaterialService.uploadMaterial(uploadMaterialRequest, user);
        return ResultResponse.success();
    }
}
