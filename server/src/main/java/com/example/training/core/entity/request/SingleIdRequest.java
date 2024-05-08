package com.example.training.core.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "单id请求")
public class SingleIdRequest {
    @Schema(description = "id")
    private String id;
}
