package com.example.training.core.entity.vo;

import com.example.training.core.entity.enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "培训用户列表项实体")
public class TrainingUserListItemVO {
    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "工号")
    private String jobId;

    @Schema(description = "姓名")
    private String userName;

    @Schema(description = "完成状态")
    private String status;

    @Schema(description = "性别")
    private GenderEnum gender;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;
}
