package com.example.training.core.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "查看培训列表返回参数")
public class FindTrainListVO {

    @Schema(description = "培训数量")
    private Long count;

    @Schema(description = "培训列表")
    private List<TrainingListItemVO> trainingList;

}
