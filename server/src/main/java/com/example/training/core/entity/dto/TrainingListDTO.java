package com.example.training.core.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TrainingListDTO {

    private String trainingName;

    private String trainingAddress;

    private Integer trainingType;

    private Date startDate;

    private Date endDate;

    private Integer offset;

    private Integer limit;

    private String queryUserId;
}
