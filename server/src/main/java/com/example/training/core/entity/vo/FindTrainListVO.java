package com.example.training.core.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "查看培训列表返回参数")
public class FindTrainListVO {
    @Schema(description = "自增id")
    private Integer id;

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
    private Integer isDel;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "完成度")
    private String Comp;

}
