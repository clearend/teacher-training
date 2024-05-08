package com.example.training.core.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TrainingUserStatusEnum {

    WAITING(100, "待完成"),
    AUDITING(150, "审核中"),
    FINISHED(200, "已完成"),
    UNFINISHED(300, "未完成");

    @EnumValue
    private final Integer code;
    @JsonValue
    private final String desc;

    public static TrainingUserStatusEnum getByCode(Integer code) {
        for (TrainingUserStatusEnum trainingUserStatusEnum : TrainingUserStatusEnum.values()) {
            if (trainingUserStatusEnum.getCode().equals(code)) {
                return trainingUserStatusEnum;
            }
        }
        return null;
    }

}
