package com.example.training.core.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "素材列表实体")
public class MaterialListVO {
    @Schema(description = "数量")
    private Long count;

    @Schema(description = "素材列表")
    private List<MaterialListItemVO> materialList;
}
