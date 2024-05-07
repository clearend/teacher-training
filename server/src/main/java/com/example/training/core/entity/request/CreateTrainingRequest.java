package com.example.training.core.entity.request;

import com.baomidou.mybatisplus.annotation.TableLogic;
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
    private LocalDateTime trainingTime;

    @Schema(description = "培训地址")
    private String trainingAddress;

    @Schema(description = "培训内容")
    private String trainingContent;

    @Schema(description = "是否删除")
    @TableLogic
    private Integer isDel;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "用户id")
    private LocalDateTime userId;
}
