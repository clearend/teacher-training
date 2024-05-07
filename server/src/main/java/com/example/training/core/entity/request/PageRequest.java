package com.example.training.core.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "分页请求参数")
public class PageRequest {
    @Schema(description = "页码", defaultValue = "1")
    private Integer currentPage = 1;

    @Schema(description = "每页条数", defaultValue = "10")
    private Integer pageSize = 10;
}
