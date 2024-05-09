package com.example.training.core.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "素材审批请求参数")
public class MaterialAuditRequest {
    @Schema(description = "素材审批id")
    private String materialAuditId;

    @Schema(description = "审批结果")
    private Integer auditResult;

    @Schema(description = "审批意见")
    private String auditRemark;
}
