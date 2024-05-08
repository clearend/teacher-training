package com.example.training.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.training.common.exceptions.BizException;
import com.example.training.common.exceptions.PermissionDenyException;
import com.example.training.core.entity.Training;
import com.example.training.core.entity.TrainingUser;
import com.example.training.core.entity.User;
import com.example.training.core.entity.enums.RoleEnum;
import com.example.training.core.entity.request.AddPersonRequest;
import com.example.training.core.entity.request.CreateTrainingRequest;
import com.example.training.core.entity.request.DeleteTrainingUserRequest;
import com.example.training.core.mapper.TrainingMapper;
import com.example.training.core.mapper.TrainingUserMapper;
import com.example.training.core.service.ITrainingUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
    @Override
    public void addPerson(AddPersonRequest createTrainingRequest,User user1) {
        List<String> userList = createTrainingRequest.getUserList();
        if(userList!=null && userList.size()>0){
            for(String user: userList){
                //查看是否已经报过名
//                String flag=trainingUserMapper.getTrainingUserById(createTrainingRequest.getTrainingId(),user.getUserId());
//                if("1".equals(flag)){
//                     continue;
//                }else{
                trainingUserMapper.addPerson(createTrainingRequest.getTrainingId(),user,user1.getUserId());
//                }
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
