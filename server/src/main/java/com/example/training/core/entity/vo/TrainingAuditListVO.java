package com.example.training.core.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "培训审核列表")
public class TrainingAuditListVO {
    @Schema(description = "数量")
    private Long count;

    @Schema(description = "培训列表")
    private List<TrainingAuditListItemVO> trainingAuditList;
}
