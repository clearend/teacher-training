package com.example.training.core.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileStatusEnum {
    AUDITING(100, "审核中"),
    PUBLISHED(200, "已发布"),
    REJECTED(300, "已驳回");

    @EnumValue
    private final Integer code;
    @JsonValue
    private final String desc;

    public static FileStatusEnum getByCode(Integer code) {
        for (FileStatusEnum fileStatusEnum : FileStatusEnum.values()) {
            if (fileStatusEnum.getCode().equals(code)) {
                return fileStatusEnum;
            }
        }
        return null;
    }
}
