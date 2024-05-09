package com.example.training.core.entity.request;

import com.example.training.core.entity.enums.MaterialTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "上传素材请求参数")
public class UploadMaterialRequest {

    @Schema(description = "素材名称")
    private String materialName;

    @Schema(description = "素材类型")
    private Integer materialType;

    @Schema(description = "文件id")
    private String fileId;

}
