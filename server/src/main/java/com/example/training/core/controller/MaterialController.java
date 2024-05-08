package com.example.training.core.controller;

import com.example.training.common.ResultResponse;
import com.example.training.common.annotations.PermissionAccess;
import com.example.training.core.entity.request.MaterialListRequest;
import com.example.training.core.entity.vo.MaterialListVO;
import com.example.training.core.service.IMaterialService;
import com.example.training.core.service.ITrainingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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
public class MaterialController {

    @Resource
    private IMaterialService iMaterialService;

    @PermissionAccess
    @PostMapping("/list")
    @Operation(summary = "查看素材列表")
    public ResultResponse<List<MaterialListVO>> findMaterialList(@RequestBody MaterialListRequest materialListRequest){
        return ResultResponse.success(iMaterialService.findMaterialList(materialListRequest));
    }

//    @PermissionAccess
//    @PostMapping("/uploadMaterial")
//    @Operation(summary = "上传素材")
//    public ResultResponse uploadMaterial(@RequestBody MaterialListRequest materialListRequest){
//        return ResultResponse.success(iMaterialService.uploadMaterial(materialListRequest));
//    }
}
