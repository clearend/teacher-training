package com.example.training.core.entity.excels;

import com.alibaba.excel.annotation.ExcelProperty;
import com.example.training.core.entity.enums.GenderEnum;
import lombok.Data;

@Data
public class UserTrainingExcel {

    @ExcelProperty("工号")
    private String jobId;

    @ExcelProperty("姓名")
    private String userName;

    @ExcelProperty("性别")
    private String gender;

    @ExcelProperty("邮箱")
    private String email;

    @ExcelProperty("手机号")
    private String phone;

    @ExcelProperty("参与培训数量")
    private int trainingCount;

    @ExcelProperty("完成培训数量")
    private int completeCount;

    @ExcelProperty("累计培训总时长（小时）")
    private int totalTrainingTime;

}
