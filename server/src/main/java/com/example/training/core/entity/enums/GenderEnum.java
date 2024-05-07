package com.example.training.core.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GenderEnum {
    UNKNOWN(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女");

    @EnumValue
    private final int code;
    @JsonValue
    private final String name;

    public static GenderEnum getByCode(int code) {
        for (GenderEnum genderEnum : GenderEnum.values()) {
            if (genderEnum.getCode() == code) {
                return genderEnum;
            }
        }
        return null;
    }
}
