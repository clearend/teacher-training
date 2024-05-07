package com.example.training.core.controller;

import com.example.training.common.ResultResponse;
import com.example.training.common.annotations.PermissionAccess;
import com.example.training.core.entity.request.CreateTrainingRequest;
import com.example.training.core.entity.request.UserListRequest;
import com.example.training.core.entity.vo.FindTrainListVO;
import com.example.training.core.entity.vo.UserListItemVO;
import com.example.training.core.service.ITrainingService;
import com.example.training.core.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

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
@RequestMapping("/core/training")
public class TrainingController {
    @Resource
    private ITrainingService iTrainingService;

    @PermissionAccess
    @PostMapping("/createTraining")
    @Operation(summary = "新建培训")
    public ResultResponse<String> createTraining(@RequestBody CreateTrainingRequest createTrainingRequest) {
        iTrainingService.createTraining(createTrainingRequest);
        return ResultResponse.success("success");
    }

    @PermissionAccess
    @PostMapping("/findTrainList")
    @Operation(summary = "查看培训列表")
    public ResultResponse<List<FindTrainListVO>> findTrainList(@RequestBody CreateTrainingRequest createTrainingRequest) {
        //入参 培训id
        return ResultResponse.success(iTrainingService.findTrainList(createTrainingRequest));
    }



}
