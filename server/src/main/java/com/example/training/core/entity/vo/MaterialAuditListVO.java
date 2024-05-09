package com.example.training.core.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "素材审核列表")
public class MaterialAuditListVO {
    @Schema(description = "数量")
    private Long count;

    @Schema(description = "审批列表")
    private List<MaterialAuditListItemVO> materialAuditList;
}
