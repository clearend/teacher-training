package com.example.training.core.entity.vo;

import com.example.training.core.entity.enums.GenderEnum;
import com.example.training.core.entity.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户列表项实体")
public class UserListItemVO {
    @Schema(description = "用户名")
    private String userId;

    @Schema(description = "用户姓名")
    private String userName;

    @Schema(description = "工号")
    private String jobId;

    @Schema(description = "角色")
    private RoleEnum role;

    @Schema(description = "性别")
    private GenderEnum gender;

    @Schema(description = "电子邮件")
    private String email;

    @Schema(description = "手机号")
    private String phone;
}
