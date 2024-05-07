package com.example.training.core.entity.request;

import com.example.training.core.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "培训增加学员请求参数")
public class AddPersonRequest {

    @Schema(description = "培训id")
    private String trainingId;

    @Schema(description = "用户列表")
    private List<User> userList;


}
