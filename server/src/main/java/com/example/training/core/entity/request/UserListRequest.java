package com.example.training.core.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户列表请求参数")
public class UserListRequest {
    private PageRequest pageRequest;
}
