package com.example.training.core.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "培训列表查询参数")
public class TrainingListRequest {

    private PageRequest pageRequest;
}
