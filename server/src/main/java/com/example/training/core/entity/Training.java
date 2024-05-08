package com.example.training.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.example.training.core.entity.enums.TrainTypeEnum;
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
    private TrainTypeEnum trainingType;

    @Schema(description = "培训时间")
    private Date trainingTime;

    @Schema(description = "培训地址")
    private String trainingAddress;

    @Schema(description = "培训内容")
    private String trainingContent;

    @Schema(description = "是否删除")
    @TableLogic
    private Integer isDel;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

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
