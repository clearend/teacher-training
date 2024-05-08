package com.example.training.core.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TrainingUserStatusEnum {

    SCHOOL(100, "待完成"),
    PROVINCE(200, "已完成"),
    NATIONAL(300, "未完成");

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
