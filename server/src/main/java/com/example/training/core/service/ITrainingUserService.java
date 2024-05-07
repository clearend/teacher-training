package com.example.training.core.service;

import com.example.training.core.entity.TrainingUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.training.core.entity.request.AddPersonRequest;
import com.example.training.core.entity.request.CreateTrainingRequest;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
public interface ITrainingUserService extends IService<TrainingUser> {

    void addPerson(AddPersonRequest addPersonRequests);

    void signUp(AddPersonRequest addPersonRequest, String user);

    void cancelSignUp(AddPersonRequest addPersonRequest, String user);
}
