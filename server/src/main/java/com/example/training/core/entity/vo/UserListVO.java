package com.example.training.core.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "用户列表实体")
public class UserListVO {
    @Schema(description = "用户数量")
    private Long count;

    @Schema(description = "用户列表")
    private List<UserListItemVO> userList;
}
