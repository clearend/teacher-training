package com.example.training.core.mapper;

import com.example.training.core.entity.TrainingAudit;
import com.example.training.core.entity.request.ExamineRequest;
import com.example.training.core.entity.request.UploadLearnRecordRequest;
import com.github.yulichang.base.MPJBaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
public interface TrainingAuditMapper extends MPJBaseMapper<TrainingAudit> {

    void examineTraining(ExamineRequest examineRequest);

    void uploadLearnRecord(UploadLearnRecordRequest request);
}
