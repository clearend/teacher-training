package com.example.training.core.service;

import com.example.training.core.entity.TrainingAudit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.training.core.entity.User;
import com.example.training.core.entity.request.ExamineRequest;
import com.example.training.core.entity.request.UploadLearnRecordRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
public interface ITrainingAuditService extends IService<TrainingAudit> {

    void examineTraining(ExamineRequest createTrainingRequest);

    void uploadLearnRecord(UploadLearnRecordRequest request, User user);
}
