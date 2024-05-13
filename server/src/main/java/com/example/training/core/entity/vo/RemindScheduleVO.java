package com.example.training.core.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RemindScheduleVO {
    @Schema(description = "自增id")
    private Integer id;

    @Schema(description = "提醒内容")
    private String remindContent;

    @Schema(description = "提醒用户id")
    private String remindUserId;

    @Schema(description = "提醒状态")
    private Integer remindStatus;

    @Schema(description = "提醒方式")
    private Integer remindType;

    @Schema(description = "邮箱")
    private String email;
}
