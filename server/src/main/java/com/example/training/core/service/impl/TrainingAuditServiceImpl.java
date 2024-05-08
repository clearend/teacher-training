package com.example.training.core.service.impl;

import cn.hutool.core.util.IdUtil;
import com.example.training.core.entity.TrainingAudit;
import com.example.training.core.entity.enums.AuditEnum;
import com.example.training.core.entity.request.ExamineRequest;
import com.example.training.core.entity.request.UploadLearnRecordRequest;
import com.example.training.core.mapper.TrainingAuditMapper;
import com.example.training.core.mapper.TrainingMapper;
import com.example.training.core.service.ITrainingAuditService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
@Service
public class TrainingAuditServiceImpl extends ServiceImpl<TrainingAuditMapper, TrainingAudit> implements ITrainingAuditService {
    @Resource
    private TrainingAuditMapper trainingAuditMapper;
    @Override
    public void examineTraining(ExamineRequest createTrainingRequest) {
        trainingAuditMapper.examineTraining(createTrainingRequest);
    }

    @Override
    public void uploadLearnRecord(UploadLearnRecordRequest request) {
        request.setAuditStatus(AuditEnum.REJECT);
        request.setIsDel(0);
        request.setTrainingAuditId("ta-" + IdUtil.fastSimpleUUID());
        trainingAuditMapper.uploadLearnRecord(request);
    }
}
