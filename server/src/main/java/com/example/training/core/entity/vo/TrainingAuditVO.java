package com.example.training.core.entity.vo;

import com.example.training.core.entity.enums.AuditEnum;
import com.example.training.core.entity.enums.TrainTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "培训审批实体")
public class TrainingAuditVO {
    @Schema(description = "培训id")
    private String trainingId;

    @Schema(description = "培训名称")
    private String trainingName;

    @Schema(description = "培训类型")
    private TrainTypeEnum trainingType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "培训时间")
    private Date trainingTime;

    @Schema(description = "培训地址")
    private String trainingAddress;

    @Schema(description = "培训内容")
    private String trainingContent;

    @Schema(description = "培训审核id")
    private String trainingAuditId;

    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "工号")
    private String JobId;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "附件id")
    private String fileId;

    @Schema(description = "附件名称")
    private String fileName;

    @Schema(description = "附件url")
    private String fileUrl;

    @Schema(description = "审核状态")
    private AuditEnum auditStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "审核时间")
    private Date auditTime;

    @Schema(description = "审核备注")
    private String auditRemark;
}
