package com.example.training.core.entity.vo;

import com.example.training.core.entity.enums.TrainTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Schema(description = "培训信息实体")
public class TrainingInfoVO {
    @Schema(description = "培训id")
    private String trainingId;

    @Schema(description = "培训名称")
    private String trainingName;

    @Schema(description = "培训类型")
    private TrainTypeEnum trainingType;

    @Schema(description = "培训时间")
    private Date trainingTime;

    @Schema(description = "培训地址")
    private String trainingAddress;

    @Schema(description = "培训内容")
    private String trainingContent;

    @Schema(description = "培训用户列表")
    private List<TrainingUserListItemVO> trainingUserList;
}
