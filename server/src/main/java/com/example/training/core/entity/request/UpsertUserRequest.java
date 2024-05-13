package com.example.training.core.entity.request;

import com.example.training.core.entity.enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户更新或插入请求参数")
public class UpsertUserRequest {
    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "工号")
    private String jobId;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "姓名")
    private String userName;

    @Schema(description = "性别")
    private GenderEnum gender;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "提醒方式")
    private Integer remindType;
}
