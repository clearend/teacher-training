package com.example.training.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * 
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
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
    private Integer auditStatus;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrainingAuditId() {
        return trainingAuditId;
    }

    public void setTrainingAuditId(String trainingAuditId) {
        this.trainingAuditId = trainingAuditId;
    }

    public String getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public LocalDateTime getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(LocalDateTime auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

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
