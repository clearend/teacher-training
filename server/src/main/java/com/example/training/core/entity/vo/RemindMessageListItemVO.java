package com.example.training.core.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class RemindMessageListItemVO {
    @Schema(description = "提醒id")
    private String remindId;

    @Schema(description = "提醒内容")
    private String remindContent;

    @Schema(description = "提醒用户id")
    private String remindUserId;

    @Schema(description = "提醒用户名称")
    private String remindUserName;

    @Schema(description = "提醒状态")
    private Integer remindStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "提醒时间")
    private Date remindTime;
}
