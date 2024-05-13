package com.example.training.core.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.training.common.exceptions.BizException;
import com.example.training.common.exceptions.PermissionDenyException;
import com.example.training.core.entity.RemindMessage;
import com.example.training.core.entity.Training;
import com.example.training.core.entity.TrainingUser;
import com.example.training.core.entity.User;
import com.example.training.core.entity.enums.RoleEnum;
import com.example.training.core.entity.request.AddPersonRequest;
import com.example.training.core.entity.request.CreateTrainingRequest;
import com.example.training.core.entity.request.DeleteTrainingUserRequest;
import com.example.training.core.mapper.RemindMessageMapper;
import com.example.training.core.mapper.TrainingMapper;
import com.example.training.core.mapper.TrainingUserMapper;
import com.example.training.core.service.ITrainingUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
@Service
public class TrainingUserServiceImpl extends ServiceImpl<TrainingUserMapper, TrainingUser> implements ITrainingUserService {
    @Resource
    private TrainingUserMapper trainingUserMapper;

    @Resource
    private TrainingMapper trainingMapper;

    @Resource
    private RemindMessageMapper remindMessageMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void addPerson(AddPersonRequest createTrainingRequest, User user1) {
        Training training = trainingMapper.selectOne(
                new LambdaQueryWrapper<Training>()
                        .eq(Training::getTrainingId, createTrainingRequest.getTrainingId())
        );
        if (training == null) {
            throw new BizException("待报名培训不存在");
        }

        String startTime = DateUtil.format(training.getTrainingTime(), "yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s = sdf.format(training.getTrainingTime());
        Date remindTime = null;
        try {
            remindTime = sdf.parse(s);
        } catch (Exception e) {
            throw new RuntimeException("时间格式转换错误");
        }

        List<String> userList = createTrainingRequest.getUserList();
        if (userList != null && !userList.isEmpty()){
            for (String userId: userList) {
                //查看是否已经报过名
                Integer flag = trainingUserMapper.getTrainingUserById(createTrainingRequest.getTrainingId(), userId);
                if (flag == null) {
                    trainingUserMapper.addPerson(createTrainingRequest.getTrainingId(), userId, user1.getUserId());

                    RemindMessage remindMessage = new RemindMessage();
                    remindMessage.setRemindId("rm-" + IdUtil.fastSimpleUUID());
                    remindMessage.setRemindContent("您已报名参与" + training.getTrainingName() + "，开始时间为：" + startTime + "，请准时参加");
                    remindMessage.setRemindTime(remindTime);
                    remindMessage.setRemindUserId(userId);
                    remindMessageMapper.insert(remindMessage);
                    continue;
                }
                if (flag == 0) {
                    throw new BizException("请勿重复参与");
                }
                if (flag == 1) {
                    trainingUserMapper.updatePerson(createTrainingRequest.getTrainingId(), userId);
                }
            }
        }
    }

    /**
     * 报名培训
     * @param addPersonRequest
     * @param user
     */
//    @Override
//    public void signUp(AddPersonRequest addPersonRequest, User user) {
//        User user1 = new User();
//        user1.setUserId(user.getUserId());
//        //查看是否已经报过名
//        String flag=trainingUserMapper.getTrainingUserById(addPersonRequest.getTrainingId(),user1.getUserId());
//        if("1".equals(flag)){
//          //如果已经被逻辑删或者已经报名
//        }else{
//            trainingUserMapper.addPerson(addPersonRequest.getTrainingId(),user1,user.getUserId());
//        }
//    }

    /**
     * 取消报名培训
     * @param addPersonRequest
     * @param user
     */
    @Override
    public void cancelSignUp(AddPersonRequest addPersonRequest, User user) {
        trainingUserMapper.cancelSignUp(addPersonRequest.getTrainingId(),user.getUserId());
    }

    @Override
    public void deletePerson(DeleteTrainingUserRequest addPersonRequest, User user) {
        TrainingUser trainingUser = trainingUserMapper.selectOne(
                new LambdaQueryWrapper<TrainingUser>()
                        .select(TrainingUser::getId, TrainingUser::getCreateUserId)
                        .eq(TrainingUser::getTrainingId, addPersonRequest.getTrainingId())
                        .eq(TrainingUser::getUserId, addPersonRequest.getUserId())
        );

        if (trainingUser == null) {
            throw new RuntimeException("待删除用户尚未参与该培训");
        }

        if (user.getRole() == RoleEnum.USER && !trainingUser.getCreateUserId().equals(user.getUserId())) {
            throw new PermissionDenyException("权限不足，不能删除管理员添加的培训");
        }

        trainingUserMapper.deleteById(trainingUser.getId());
    }
}
