package com.example.training.core.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TrainTypeEnum {
    SCHOOL(100, "校级"),
    PROVINCE(200, "省市级"),
    NATIONAL(300, "国家级");

    @EnumValue
    private final Integer code;
    @JsonValue
    private final String desc;

    public static TrainTypeEnum getByCode(Integer code) {
        for (TrainTypeEnum trainTypeEnum : TrainTypeEnum.values()) {
            if (trainTypeEnum.getCode().equals(code)) {
                return trainTypeEnum;
            }
        }
        return null;
    }
}
