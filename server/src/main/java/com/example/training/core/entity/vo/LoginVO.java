package com.example.training.core.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "登录实体")
public class LoginVO {
    @Schema(description = "用户名")
    private String userId;

    @Schema(description = "工号")
    private String jobId;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "电子邮件")
    private String email;

    @Schema(description = "手机号")
    private String phone;
}
