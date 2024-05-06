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
@TableName("material_audit")
@Schema(name = "MaterialAudit", description = "")
public class MaterialAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "素材审核id")
    private String materialAuditId;

    @Schema(description = "素材id")
    private String materialId;

    @Schema(description = "审核用户id")
    private String auditUserId;

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

    public String getMaterialAuditId() {
        return materialAuditId;
    }

    public void setMaterialAuditId(String materialAuditId) {
        this.materialAuditId = materialAuditId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
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
        return "MaterialAudit{" +
            "id = " + id +
            ", materialAuditId = " + materialAuditId +
            ", materialId = " + materialId +
            ", auditUserId = " + auditUserId +
            ", auditStatus = " + auditStatus +
            ", auditTime = " + auditTime +
            ", auditRemark = " + auditRemark +
            ", isDel = " + isDel +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
        "}";
    }
}
