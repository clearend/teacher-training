package com.example.training.core.service;

import com.example.training.core.entity.TrainingAudit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.training.core.entity.User;
import com.example.training.core.entity.request.ExamineRequest;
import com.example.training.core.entity.request.SingleIdRequest;
import com.example.training.core.entity.request.TrainingAuditListRequest;
import com.example.training.core.entity.request.UploadLearnRecordRequest;
import com.example.training.core.entity.vo.SysFileVO;
import com.example.training.core.entity.vo.TrainingAuditListVO;
import com.example.training.core.entity.vo.TrainingAuditVO;

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

    void cancelRecord(SingleIdRequest singleIdRequest, User user);

    TrainingAuditVO getAuditInfo(SingleIdRequest singleIdRequest, User user);

    TrainingAuditListVO getTrainingAuditList(TrainingAuditListRequest trainingAuditListRequest, User user);
}
