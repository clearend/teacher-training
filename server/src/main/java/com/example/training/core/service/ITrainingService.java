package com.example.training.core.service;

import com.example.training.core.entity.Training;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.training.core.entity.User;
import com.example.training.core.entity.request.*;
import com.example.training.core.entity.vo.FindTrainListVO;
import com.example.training.core.entity.vo.TrainingInfoVO;
import com.example.training.core.entity.vo.UserListItemVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
public interface ITrainingService extends IService<Training> {

    void createTraining(CreateTrainingRequest createTrainingRequest);

    FindTrainListVO findTrainList(TrainingListRequest createTrainingRequest, User user);

    void deleteTraining(SingleIdRequest singleIdRequest);

    TrainingInfoVO findTrainingInfo(SingleIdRequest singleIdRequest);

    List<UserListItemVO> findNotInUserList(SingleIdRequest singleIdRequest);

    void updateTraining(CreateTrainingRequest createTrainingRequest);

    FindTrainListVO getTrainingListUser(TrainingListRequest trainingListRequest, User user);
}
