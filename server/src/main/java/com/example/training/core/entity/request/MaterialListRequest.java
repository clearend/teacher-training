package com.example.training.core.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "材料列表请求参数")
public class MaterialListRequest {
    private PageRequest pageRequest;
}
