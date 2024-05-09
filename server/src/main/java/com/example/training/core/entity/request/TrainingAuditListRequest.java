package com.example.training.core.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "获取培训审批列表请求参数")
public class TrainingAuditListRequest {
    private PageRequest pageRequest;
}
