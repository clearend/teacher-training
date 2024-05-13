package com.example.training.core.controller;

import com.alibaba.excel.EasyExcel;
import com.example.training.common.ResultResponse;
import com.example.training.common.annotations.PermissionAccess;
import com.example.training.core.entity.User;
import com.example.training.core.entity.excels.UserTrainingExcel;
import com.example.training.core.entity.request.AddPersonRequest;
import com.example.training.core.entity.request.DeleteTrainingUserRequest;
import com.example.training.core.service.ITrainingUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    @GetMapping("/export")
    @Operation(summary = "导出用户培训记录")
    public void export(HttpServletResponse response) throws IOException {
        List<UserTrainingExcel> excels = iTrainingUserService.export();

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("教师培训报表", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), UserTrainingExcel.class).sheet("Sheet1").doWrite(excels);
    }

}
