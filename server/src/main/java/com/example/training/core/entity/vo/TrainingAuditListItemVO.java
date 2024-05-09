package com.example.training.core.entity.vo;

import com.example.training.core.entity.enums.AuditEnum;
import com.example.training.core.entity.enums.TrainTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "培训审核列表项")
public class TrainingAuditListItemVO {
    @Schema(description = "培训审核id")
    private String trainingAuditId;

    @Schema(description = "培训id")
    private String trainingId;

    @Schema(description = "培训名称")
    private String trainingName;

    @Schema(description = "培训类型")
    private TrainTypeEnum trainingType;

    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "审核状态")
    private AuditEnum auditStatus;

    @Schema(description = "学习时长")
    private String learnTime;
}
