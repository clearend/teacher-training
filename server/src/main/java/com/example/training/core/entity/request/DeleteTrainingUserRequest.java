package com.example.training.core.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "删除培训用户请求参数")
public class DeleteTrainingUserRequest {
    @Schema(description = "培训id")
    private String trainingId;

    @Schema(description = "用户id")
    private String userId;
}
