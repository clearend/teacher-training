package com.example.training.core.controller;

import com.example.training.common.ResultResponse;
import com.example.training.common.annotations.PermissionAccess;
import com.example.training.core.entity.request.AddPersonRequest;
import com.example.training.core.entity.request.CreateTrainingRequest;
import com.example.training.core.service.ITrainingService;
import com.example.training.core.service.ITrainingUserService;
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
@RequestMapping("/core/trainingUser")
public class TrainingUserController {

    @Resource
    private ITrainingUserService iTrainingUserService;

    @PermissionAccess
    @PostMapping("/addPerson")
    @Operation(summary = "加入学员")
    public ResultResponse addPerson(@RequestBody AddPersonRequest addPersonRequest,@RequestAttribute("user") String user) {
        iTrainingUserService.addPerson(addPersonRequest);
        return ResultResponse.success("success");
    }

    @PostMapping("/signUp")
    @PermissionAccess
    @Operation(summary = "报名培训")
    public ResultResponse signUp(@RequestBody AddPersonRequest addPersonRequest,@RequestAttribute("user") String user) {
        iTrainingUserService.signUp(addPersonRequest,user);
        return ResultResponse.success("success");
    }

    @PostMapping("/cancelSignUp")
    @PermissionAccess
    @Operation(summary = "取消报名培训")
    public ResultResponse cancelSignUp(@RequestBody AddPersonRequest addPersonRequest,@RequestAttribute("user") String user) {
        iTrainingUserService.cancelSignUp(addPersonRequest,user);
        return ResultResponse.success("success");
    }
}
