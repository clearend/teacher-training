package com.example.training.core.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "素材审批列表请求参数")
public class MaterialAuditListRequest {
    private PageRequest pageRequest;
}
