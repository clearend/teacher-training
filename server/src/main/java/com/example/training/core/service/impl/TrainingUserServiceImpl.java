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
    public void addPerson(AddPersonRequest createTrainingRequest) {
        List<User> userList = createTrainingRequest.getUserList();
        if(userList!=null && userList.size()>0){
            for(User user:userList){
                trainingUserMapper.addPerson(createTrainingRequest.getTrainingId(),user);
            }
        }
    }

    /**
     * 报名培训
     * @param addPersonRequest
     * @param user
     */
    @Override
    public void signUp(AddPersonRequest addPersonRequest, String user) {
        User user1 = new User();
        user1.setUserId(user);
        trainingUserMapper.addPerson(addPersonRequest.getTrainingId(),user1);
    }

    /**
     * 取消报名培训
     * @param addPersonRequest
     * @param user
     */
    @Override
    public void cancelSignUp(AddPersonRequest addPersonRequest, String user) {
        trainingUserMapper.cancelSignUp(addPersonRequest.getTrainingId(),user);
    }


}
