package com.example.training.core.service;

import com.example.training.core.entity.TrainingUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.training.core.entity.User;
import com.example.training.core.entity.excels.UserTrainingExcel;
import com.example.training.core.entity.request.AddPersonRequest;
import com.example.training.core.entity.request.CreateTrainingRequest;
import com.example.training.core.entity.request.DeleteTrainingUserRequest;

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

    void addPerson(AddPersonRequest addPersonRequests,User user);

//    void signUp(AddPersonRequest addPersonRequest, User user);

    void cancelSignUp(AddPersonRequest addPersonRequest, User user);

    void deletePerson(DeleteTrainingUserRequest addPersonRequest, User user);

    List<UserTrainingExcel> export();
}
