package com.example.training.core.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "上报学习记录请求参数")
public class UploadLearnRecordRequest {

    @Schema(description = "培训id")
    private String trainingId;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "附件id")
    private String fileId;

}
