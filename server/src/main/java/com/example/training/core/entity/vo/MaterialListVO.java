package com.example.training.core.entity.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.example.training.core.entity.enums.AuditEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "材料实体")
public class MaterialListVO {

    @Schema(description = "素材id")
    private String materialId;

    @Schema(description = "素材名称")
    private String materialName;

    @Schema(description = "素材类型")
    private Integer materialType;

    @Schema(description = "上传用户id")
    private String userId;

    @Schema(description = "文件id")
    private String fileId;

    @Schema(description = "文件状态")
    private AuditEnum fileStatus;

    @Schema(description = "是否删除")
    @TableLogic
    private Integer isDel;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
