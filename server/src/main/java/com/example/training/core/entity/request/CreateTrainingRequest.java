package com.example.training.core.entity.request;

import com.example.training.core.entity.enums.TrainTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "新建培训请求参数")
public class CreateTrainingRequest {

    @Schema(description = "培训id")
    private String trainingId;

    @Schema(description = "培训名称")
    private String trainingName;

    @Schema(description = "培训类型")
    private Integer trainingType;

    @Schema(description = "培训时间")
    private Long trainingTime;

    @Schema(description = "培训地址")
    private String trainingAddress;

    @Schema(description = "培训内容")
    private String trainingContent;

}
