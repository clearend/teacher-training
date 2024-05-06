package com.example.training.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * 
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
@Schema(name = "Training", description = "")
public class Training implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "培训id")
    private String trainingId;

    @Schema(description = "培训名称")
    private String trainingName;

    @Schema(description = "培训类型")
    private Integer trainingType;

    @Schema(description = "培训时间")
    private LocalDateTime trainingTime;

    @Schema(description = "培训地址")
    private String trainingAddress;

    @Schema(description = "培训内容")
    private String trainingContent;

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

    public String getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public Integer getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(Integer trainingType) {
        this.trainingType = trainingType;
    }

    public LocalDateTime getTrainingTime() {
        return trainingTime;
    }

    public void setTrainingTime(LocalDateTime trainingTime) {
        this.trainingTime = trainingTime;
    }

    public String getTrainingAddress() {
        return trainingAddress;
    }

    public void setTrainingAddress(String trainingAddress) {
        this.trainingAddress = trainingAddress;
    }

    public String getTrainingContent() {
        return trainingContent;
    }

    public void setTrainingContent(String trainingContent) {
        this.trainingContent = trainingContent;
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
        return "Training{" +
            "id = " + id +
            ", trainingId = " + trainingId +
            ", trainingName = " + trainingName +
            ", trainingType = " + trainingType +
            ", trainingTime = " + trainingTime +
            ", trainingAddress = " + trainingAddress +
            ", trainingContent = " + trainingContent +
            ", isDel = " + isDel +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
        "}";
    }
}
