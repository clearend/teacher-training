package com.example.training.core.service.impl;

import com.example.training.core.entity.TrainingUser;
import com.example.training.core.entity.User;
import com.example.training.core.entity.request.AddPersonRequest;
import com.example.training.core.entity.request.CreateTrainingRequest;
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
        List<User> userList = createTrainingRequest.getUserList();
        if(userList!=null && userList.size()>0){
            for(User user:userList){
                //查看是否已经报过名
                String flag=trainingUserMapper.getTrainingUserById(createTrainingRequest.getTrainingId(),user.getUserId());
                if("1".equals(flag)){
                     continue;
                }else{
                    trainingUserMapper.addPerson(createTrainingRequest.getTrainingId(),user,user1.getUserId());
                }
            }
        }
    }

    /**
     * 报名培训
     * @param addPersonRequest
     * @param user
     */
    @Override
    public void signUp(AddPersonRequest addPersonRequest, User user) {
        User user1 = new User();
        user1.setUserId(user.getUserId());
        //查看是否已经报过名
        String flag=trainingUserMapper.getTrainingUserById(addPersonRequest.getTrainingId(),user1.getUserId());
        if("1".equals(flag)){
          //如果已经被逻辑删或者已经报名
        }else{
            trainingUserMapper.addPerson(addPersonRequest.getTrainingId(),user1,user.getUserId());
        }
    }

    /**
     * 取消报名培训
     * @param addPersonRequest
     * @param user
     */
    @Override
    public void cancelSignUp(AddPersonRequest addPersonRequest, User user) {
        trainingUserMapper.cancelSignUp(addPersonRequest.getTrainingId(),user.getUserId());
    }

    /**
     * 删除培训用户
     * @param addPersonRequest
     * @param user
     */
    @Override
    public void deletePerson(AddPersonRequest addPersonRequest, User user) {
        List<User> userList = addPersonRequest.getUserList();
        if(userList!=null && userList.size()>0){
            for(User user1:userList){
                trainingUserMapper.cancelSignUp(addPersonRequest.getTrainingId(),user1.getUserId());
            }
        }
    }
}
