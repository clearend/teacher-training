package com.example.training.core.controller;

import com.example.training.common.ResultResponse;
import com.example.training.common.annotations.PermissionAccess;
import com.example.training.core.entity.User;
import com.example.training.core.entity.request.AddPersonRequest;
import com.example.training.core.entity.request.CreateTrainingRequest;
import com.example.training.core.entity.request.DeleteTrainingUserRequest;
import com.example.training.core.service.ITrainingService;
import com.example.training.core.service.ITrainingUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/core/trainingUser")
@CrossOrigin
@ControllerAdvice
@Tag(name = "培训用户控制器")
public class TrainingUserController {

    @Resource
    private ITrainingUserService iTrainingUserService;

    @PermissionAccess
    @PostMapping("/addPerson")
    @Operation(summary = "加入培训用户")
    public ResultResponse addPerson(@RequestBody AddPersonRequest addPersonRequest,@RequestAttribute("user") User user) {
        iTrainingUserService.addPerson(addPersonRequest,user);
        return ResultResponse.success("success");
    }

    @PostMapping("/deletePerson")
    @PermissionAccess
    @Operation(summary = "删除培训用户")
    public ResultResponse<?> deletePerson(
            @RequestBody DeleteTrainingUserRequest deleteTrainingUserRequest,
            @RequestAttribute("user") User user
    ) {
        iTrainingUserService.deletePerson(deleteTrainingUserRequest, user);
        return ResultResponse.success("success");
    }

//    @PostMapping("/signUp")
//    @PermissionAccess
//    @Operation(summary = "报名培训")
//    public ResultResponse signUp(@RequestBody AddPersonRequest addPersonRequest,@RequestAttribute("user") User user) {
//        iTrainingUserService.signUp(addPersonRequest,user);
//        return ResultResponse.success("success");
//    }

    @PostMapping("/cancelSignUp")
    @PermissionAccess
    @Operation(summary = "取消报名培训")
    public ResultResponse cancelSignUp(@RequestBody AddPersonRequest addPersonRequest,@RequestAttribute("user") User user) {
        iTrainingUserService.cancelSignUp(addPersonRequest,user);
        return ResultResponse.success("success");
    }


}
