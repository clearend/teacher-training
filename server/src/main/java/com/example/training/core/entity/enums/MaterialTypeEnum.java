package com.example.training.core.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MaterialTypeEnum {
    DOCUMENT(0, "文档"),
    PICTURE(1, "图片"),
    VIDEO(2, "视频"),
    AUDIO(3, "音频"),
    OTHER(4, "其他");

    @EnumValue
    private final Integer code;
    @JsonValue
    private final String desc;

    public static MaterialTypeEnum getByCode(Integer code) {
        for (MaterialTypeEnum materialTypeEnum : MaterialTypeEnum.values()) {
            if (materialTypeEnum.getCode().equals(code)) {
                return materialTypeEnum;
            }
        }
        return null;
    }
}
