package com.example.training.core.entity.request;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.example.training.core.entity.TrainingAudit;
import com.example.training.core.entity.enums.AuditEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Schema(description = "上报学习记录请求参数")
public class UploadLearnRecordRequest {

    @Schema(description = "培训审核id")
    private String trainingAuditId;

    @Schema(description = "培训id")
    private String trainingId;

    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "附件id")
    private String fileId;

    @Schema(description = "审核状态")
    private AuditEnum auditStatus;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核备注")
    private String auditRemark;

    @Schema(description = "是否删除")
    @TableLogic
    private Integer isDel;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

}
