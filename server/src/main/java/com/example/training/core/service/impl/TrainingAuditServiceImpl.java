package com.example.training.core.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.training.common.exceptions.BizException;
import com.example.training.core.entity.*;
import com.example.training.core.entity.enums.AuditEnum;
import com.example.training.core.entity.enums.RoleEnum;
import com.example.training.core.entity.enums.TrainingUserStatusEnum;
import com.example.training.core.entity.request.ExamineRequest;
import com.example.training.core.entity.request.SingleIdRequest;
import com.example.training.core.entity.request.TrainingAuditListRequest;
import com.example.training.core.entity.request.UploadLearnRecordRequest;
import com.example.training.core.entity.vo.TrainingAuditListItemVO;
import com.example.training.core.entity.vo.TrainingAuditListVO;
import com.example.training.core.entity.vo.TrainingAuditVO;
import com.example.training.core.mapper.TrainingAuditMapper;
import com.example.training.core.mapper.TrainingMapper;
import com.example.training.core.mapper.TrainingUserMapper;
import com.example.training.core.service.ITrainingAuditService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    @Transactional(rollbackFor = {Exception.class})
    public void examineTraining(ExamineRequest createTrainingRequest) {
        TrainingAudit trainingAudit = trainingAuditMapper.selectOne(
                new LambdaQueryWrapper<TrainingAudit>()
                        .select(TrainingAudit::getId, TrainingAudit::getTrainingId, TrainingAudit::getUserId)
                        .eq(TrainingAudit::getTrainingAuditId, createTrainingRequest.getTrainingAuditId())
        );

        trainingAudit.setAuditStatus(createTrainingRequest.getAuditResult() == 0 ? AuditEnum.REJECT : AuditEnum.PASS);
        trainingAudit.setAuditRemark(createTrainingRequest.getAuditRemark());
        trainingAuditMapper.updateById(trainingAudit);

        TrainingUser trainingUser = trainingUserMapper.selectOne(
                new LambdaQueryWrapper<TrainingUser>()
                        .select(TrainingUser::getId)
                        .eq(TrainingUser::getTrainingId, trainingAudit.getTrainingId())
                        .eq(TrainingUser::getUserId, trainingAudit.getUserId())
        );
        trainingUser.setStatus(createTrainingRequest.getAuditResult() == 0 ? TrainingUserStatusEnum.UNFINISHED : TrainingUserStatusEnum.FINISHED);
        trainingUserMapper.updateById(trainingUser);
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

    @Override
    public void cancelRecord(SingleIdRequest singleIdRequest, User user) {
        TrainingAudit trainingAudit = trainingAuditMapper.selectOne(
                new LambdaQueryWrapper<TrainingAudit>()
                        .select(TrainingAudit::getId, TrainingAudit::getAuditStatus)
                        .eq(TrainingAudit::getTrainingId, singleIdRequest.getId())
                        .eq(TrainingAudit::getUserId, user.getUserId())
        );

        if (trainingAudit == null) {
            throw new BizException("待撤销心得不存在");
        }

        if (trainingAudit.getAuditStatus() != AuditEnum.WAIT) {
            throw new BizException("上报心得已审核，无法撤销");
        }

        TrainingUser trainingUser = trainingUserMapper.selectOne(
                new LambdaQueryWrapper<TrainingUser>()
                        .select(TrainingUser::getId, TrainingUser::getStatus)
                        .eq(TrainingUser::getTrainingId, singleIdRequest.getId())
                        .eq(TrainingUser::getUserId, user.getUserId())
        );
        trainingUser.setStatus(TrainingUserStatusEnum.WAITING);
        trainingUserMapper.updateById(trainingUser);

        trainingAuditMapper.deleteById(trainingAudit.getId());
        trainingUserMapper.updateById(trainingUser);
    }

    @Override
    public TrainingAuditVO getAuditInfo(SingleIdRequest singleIdRequest, User user) {
        MPJLambdaWrapper<TrainingAudit> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(TrainingAudit::getTrainingAuditId, TrainingAudit::getContent, TrainingAudit::getAuditRemark, TrainingAudit::getAuditStatus, TrainingAudit::getAuditTime)
                .select(Training::getTrainingId, Training::getTrainingName, Training::getTrainingAddress, Training::getTrainingContent, Training::getTrainingType, Training::getTrainingTime)
                .select(User::getUserId, User::getUserName, User::getJobId)
                .select(SysFile::getFileId, SysFile::getFileName, SysFile::getFileUrl)
                .leftJoin(Training.class, Training::getTrainingId, TrainingAudit::getTrainingId)
                .leftJoin(User.class, User::getUserId, TrainingAudit::getUserId)
                .leftJoin(SysFile.class, SysFile::getFileId, TrainingAudit::getFileId);

        if (user.getRole() == RoleEnum.USER) {
            wrapper.eq(TrainingAudit::getTrainingId, singleIdRequest.getId())
                    .eq(TrainingAudit::getUserId, user.getUserId());
        } else {
            wrapper.eq(TrainingAudit::getTrainingAuditId, singleIdRequest.getId());
        }

        TrainingAuditVO trainingAuditVO = trainingAuditMapper.selectJoinOne(TrainingAuditVO.class, wrapper);

        if (Objects.isNull(trainingAuditVO)) {
            throw new BizException("待查询培训审核不存在");
        }

        return trainingAuditVO;
    }

    @Override
    public TrainingAuditListVO getTrainingAuditList(TrainingAuditListRequest trainingAuditListRequest, User user) {
        MPJLambdaWrapper<TrainingAudit> wrapper = new MPJLambdaWrapper<>();
        Integer currentPage = trainingAuditListRequest.getPageRequest().getCurrentPage();
        Integer pageSize = trainingAuditListRequest.getPageRequest().getPageSize();
        Integer offset = (currentPage - 1) * pageSize;

        wrapper.leftJoin(Training.class, Training::getTrainingId, TrainingAudit::getTrainingId)
                .leftJoin(User.class, User::getUserId, TrainingAudit::getUserId)
                .leftJoin(TrainingUser.class, on -> on.eq(TrainingUser::getUserId, TrainingAudit::getUserId).eq(TrainingUser::getTrainingId, TrainingAudit::getTrainingId));

        TrainingAuditListVO trainingAuditListVO = new TrainingAuditListVO();
        trainingAuditListVO.setCount(trainingAuditMapper.selectJoinCount(wrapper));

        wrapper.select(TrainingAudit::getTrainingAuditId, TrainingAudit::getAuditStatus)
                .select(Training::getTrainingId, Training::getTrainingName, Training::getTrainingType)
                .select(User::getUserId, User::getUserName)
                .select(TrainingUser::getLearnTime)
                .orderByAsc(TrainingAudit::getAuditStatus)
                .last("limit " + pageSize + " offset " + ((currentPage - 1) * pageSize));
        List<TrainingAuditListItemVO> trainingAuditListItemVOList = trainingAuditMapper.selectJoinList(TrainingAuditListItemVO.class, wrapper);
        if (Objects.nonNull(trainingAuditListItemVOList) && !trainingAuditListItemVOList.isEmpty()) {
            trainingAuditListVO.setTrainingAuditList(trainingAuditListItemVOList);
        } else {
            trainingAuditListVO.setTrainingAuditList(Collections.emptyList());
        }

        return trainingAuditListVO;
    }
}
