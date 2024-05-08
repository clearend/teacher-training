package com.example.training.core.controller;

import com.example.training.common.ResultResponse;
import com.example.training.common.annotations.PermissionAccess;
import com.example.training.core.entity.User;
import com.example.training.core.entity.request.*;
import com.example.training.core.entity.vo.FindTrainListVO;
import com.example.training.core.entity.vo.TrainingInfoVO;
import com.example.training.core.entity.vo.UserListItemVO;
import com.example.training.core.service.ITrainingService;
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
@RequestMapping("/core/training")
@ControllerAdvice
@CrossOrigin
@Tag(name = "培训控制器")
public class TrainingController {

    @Resource
    private ITrainingService iTrainingService;

    @PermissionAccess
    @PostMapping("/create")
    @Operation(summary = "新建培训")
    public ResultResponse<String> createTraining(@RequestBody CreateTrainingRequest createTrainingRequest) {
        iTrainingService.createTraining(createTrainingRequest);
        return ResultResponse.success("success");
    }

    @PermissionAccess
    @PostMapping("/findTrainList")
    @Operation(summary = "查看培训列表")
    public ResultResponse<FindTrainListVO> findTrainList(
            @RequestBody TrainingListRequest trainingListRequest,
            @RequestAttribute("user") User user
    ) {
        //入参 培训id
        return ResultResponse.success(iTrainingService.findTrainList(trainingListRequest, user));
    }

    @PermissionAccess
    @PostMapping("/list/user")
    @Operation(summary = "查看培训列表用户")
    public ResultResponse<FindTrainListVO> findUserList(
            @RequestBody TrainingListRequest trainingListRequest,
            @RequestAttribute("user") User user
    ) {
        return ResultResponse.success(iTrainingService.getTrainingListUser(trainingListRequest, user));
    }

    @PermissionAccess
    @PostMapping("/notInUserList")
    @Operation(summary = "查看未参加培训人员列表")
    public ResultResponse<List<UserListItemVO>> findNotInUserList(@RequestBody SingleIdRequest singleIdRequest) {
        return ResultResponse.success(iTrainingService.findNotInUserList(singleIdRequest));
    }

    @PermissionAccess
    @PostMapping("/delete")
    @Operation(summary = "删除培训")
    public ResultResponse<String> deleteTraining(@RequestBody SingleIdRequest singleIdRequest) {
        iTrainingService.deleteTraining(singleIdRequest);
        return ResultResponse.success();
    }

    @PermissionAccess
    @PostMapping("/info")
    @Operation(summary = "查看培训详情")
    public ResultResponse<TrainingInfoVO> findTrainingInfo(@RequestBody SingleIdRequest singleIdRequest) {
        return ResultResponse.success(iTrainingService.findTrainingInfo(singleIdRequest));
    }

    @PermissionAccess
    @PostMapping("/update")
    @Operation(summary = "修改培训")
    public ResultResponse<String> updateTraining(@RequestBody CreateTrainingRequest createTrainingRequest) {
        iTrainingService.updateTraining(createTrainingRequest);
        return ResultResponse.success("success");
    }

}
