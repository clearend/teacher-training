package com.example.training.core.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "提醒消息列表请求")
public class RemindMessageListRequest {
    private PageRequest pageRequest;
}
