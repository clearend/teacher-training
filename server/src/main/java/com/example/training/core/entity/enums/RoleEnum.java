package com.example.training.core.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleEnum {
    USER(0, "user"),
    ADMIN(1, "admin");

    @EnumValue
    private final Integer code;
    @JsonValue
    private final String desc;
}
