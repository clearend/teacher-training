package com.example.training.core.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class RemindMessageListVO {
    private Long count;

    private List<RemindMessageListItemVO> remindList;
}
