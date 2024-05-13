package com.example.training.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author coder
 * @since 2024-05-13
 */
@Data
@TableName("remind_message")
@Schema(name = "RemindMessage", description = "")
public class RemindMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "提醒id")
    private String remindId;

    @Schema(description = "提醒内容")
    private String remindContent;

    @Schema(description = "提醒用户id")
    private String remindUserId;

    @Schema(description = "提醒状态")
    private Integer remindStatus;

    @Schema(description = "提醒时间")
    private Date remindTime;

    @Schema(description = "是否删除")
    private Integer isDel;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Override
    public String toString() {
        return "RemindMessage{" +
            "id = " + id +
            ", remindId = " + remindId +
            ", remindContent = " + remindContent +
            ", remindUserId = " + remindUserId +
            ", remindStatus = " + remindStatus +
            ", remindTime = " + remindTime +
            ", isDel = " + isDel +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
        "}";
    }
}
