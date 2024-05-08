package com.example.training.core.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "未参与用户列表")
public class NotInUserListRequest {
    @Schema(description = "培训id")
    private String trainingId;

    @Schema(description = "排除用户id列表")
    private List<String> excludeUserIds;
}
