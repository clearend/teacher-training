package com.example.training.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.example.training.core.entity.enums.GenderEnum;
import com.example.training.core.entity.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
@Data
@Schema(name = "User", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "工号")
    private String jobId;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "姓名")
    private String userName;

    @Schema(description = "性别")
    private GenderEnum gender;

    @Schema(description = "角色")
    private RoleEnum role;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "是否删除")
    @TableLogic
    private Integer isDel;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Override
    public String toString() {
        return "User{" +
            "id = " + id +
            ", userId = " + userId +
            ", jobId = " + jobId +
            ", password = " + password +
            ", userName = " + userName +
            ", gender = " + gender +
            ", role = " + role +
            ", email = " + email +
            ", phone = " + phone +
            ", isDel = " + isDel +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
        "}";
    }
}
