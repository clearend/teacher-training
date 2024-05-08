package com.example.training.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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
@TableName("training_audit")
@Schema(name = "TrainingAudit", description = "")
public class TrainingAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
    private Date auditTime;

    @Schema(description = "审核备注")
    private String auditRemark;

    @Schema(description = "是否删除")
    @TableLogic
    private Integer isDel;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Override
    public String toString() {
        return "TrainingAudit{" +
            "id = " + id +
            ", trainingAuditId = " + trainingAuditId +
            ", trainingId = " + trainingId +
            ", userId = " + userId +
            ", content = " + content +
            ", fileId = " + fileId +
            ", auditStatus = " + auditStatus +
            ", auditTime = " + auditTime +
            ", auditRemark = " + auditRemark +
            ", isDel = " + isDel +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
        "}";
    }
}
