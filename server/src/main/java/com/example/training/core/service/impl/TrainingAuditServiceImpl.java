package com.example.training.core.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.training.common.exceptions.BizException;
import com.example.training.core.entity.TrainingAudit;
import com.example.training.core.entity.TrainingUser;
import com.example.training.core.entity.User;
import com.example.training.core.entity.enums.AuditEnum;
import com.example.training.core.entity.enums.TrainingUserStatusEnum;
import com.example.training.core.entity.request.ExamineRequest;
import com.example.training.core.entity.request.UploadLearnRecordRequest;
import com.example.training.core.mapper.TrainingAuditMapper;
import com.example.training.core.mapper.TrainingMapper;
import com.example.training.core.mapper.TrainingUserMapper;
import com.example.training.core.service.ITrainingAuditService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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

    @Resource
    private TrainingUserMapper trainingUserMapper;

    @Override
    public void examineTraining(ExamineRequest createTrainingRequest) {
        trainingAuditMapper.examineTraining(createTrainingRequest);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class})
    public void uploadLearnRecord(UploadLearnRecordRequest request, User user) {
        TrainingAudit trainingAudit = trainingAuditMapper.selectOne(
                new LambdaQueryWrapper<TrainingAudit>()
                        .eq(TrainingAudit::getTrainingId, request.getTrainingId())
                        .eq(TrainingAudit::getUserId, user.getUserId())
        );

        if (trainingAudit != null) {
            throw new BizException("请勿重复提交学习心得");
        }

        TrainingUser trainingUser = trainingUserMapper.selectOne(
                new LambdaQueryWrapper<TrainingUser>()
                        .select(TrainingUser::getId, TrainingUser::getStatus, TrainingUser::getCreateTime)
                        .eq(TrainingUser::getTrainingId, request.getTrainingId())
                        .eq(TrainingUser::getUserId, user.getUserId())
        );
        trainingUser.setStatus(TrainingUserStatusEnum.AUDITING);
        LocalDateTime startTime = trainingUser.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);
        trainingUser.setLearnTime(duration.toHours() + "h");
        trainingUserMapper.updateById(trainingUser);

        trainingAudit = new TrainingAudit();
        trainingAudit.setTrainingAuditId("ta-" + IdUtil.fastSimpleUUID());
        trainingAudit.setAuditStatus(AuditEnum.WAIT);
        trainingAudit.setContent(request.getContent());
        trainingAudit.setUserId(user.getUserId());
        trainingAudit.setTrainingId(request.getTrainingId());
        trainingAudit.setFileId(request.getFileId());
        trainingAuditMapper.insert(trainingAudit);

//        request.setAuditStatus(AuditEnum.REJECT);
//        request.setIsDel(0);
//        request.setTrainingAuditId();
//        trainingAuditMapper.uploadLearnRecord(request);
    }
}
