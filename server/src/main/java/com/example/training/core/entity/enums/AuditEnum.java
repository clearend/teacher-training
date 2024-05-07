package com.example.training.core.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuditEnum {
    WAIT(100, "审核中"),
    PASS(200, "已通过"),
    REJECT(300, "未通过");

    @EnumValue
    private final Integer code;
    @JsonValue
    private final String desc;
}
