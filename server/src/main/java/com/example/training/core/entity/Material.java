package com.example.training.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.example.training.core.entity.enums.AuditEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
@Setter
@Getter
@Schema(name = "Material", description = "")
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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

    @Override
    public String toString() {
        return "Material{" +
            "id = " + id +
            ", materialId = " + materialId +
            ", materialName = " + materialName +
            ", materialType = " + materialType +
            ", userId = " + userId +
            ", fileId = " + fileId +
            ", fileStatus = " + fileStatus +
            ", isDel = " + isDel +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
        "}";
    }
}
