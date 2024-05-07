package com.example.training.core.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "文件信息实体")
public class SysFileVO {
    @Schema(description = "文件id")
    private String fileId;

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "文件url")
    private String fileUrl;
}
