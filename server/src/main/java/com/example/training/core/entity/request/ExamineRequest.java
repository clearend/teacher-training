package com.example.training.core.entity.request;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.example.training.core.entity.enums.AuditEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "审核培训请求参数")
public class ExamineRequest {
    @Schema(description = "培训审核id")
    private String trainingAuditId;

    @Schema(description = "审核结果")
    private Integer auditResult;

    @Schema(description = "审核意见")
    private String auditRemark;

}
