package com.example.training.core.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "添加提醒请求参数")
public class AddRemindRequest {
    @Schema(description = "提醒内容")
    private String remindContent;

    @Schema(description = "提醒用户id")
    private List<String> remindUserIds;

    @Schema(description = "提醒时间")
    private Long remindTime;
}
