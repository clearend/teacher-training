package com.example.training.core.entity.vo;

import com.example.training.core.entity.enums.AuditEnum;
import com.example.training.core.entity.enums.MaterialTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "素材审批列表项请求参数")
public class MaterialAuditListItemVO {

    @Schema(description = "素材审批id")
    private String materialAuditId;

    @Schema(description = "审核状态")
    private AuditEnum auditStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "审核时间")
    private Date auditTime;

    @Schema(description = "审核备注")
    private String auditRemark;

    @Schema(description = "素材id")
    private String materialId;

    @Schema(description = "素材名称")
    private String materialName;

    @Schema(description = "素材类型")
    private MaterialTypeEnum materialType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "上传时间")
    private Date uploadTime;

    @Schema(description = "上传用户id")
    private String userId;

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "文件id")
    private String fileId;

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "文件url")
    private String fileUrl;
}