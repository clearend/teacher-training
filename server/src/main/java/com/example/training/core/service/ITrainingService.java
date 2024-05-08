package com.example.training.core.service;

import com.example.training.core.entity.Training;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.training.core.entity.request.CreateTrainingRequest;
import com.example.training.core.entity.request.SingleIdRequest;
import com.example.training.core.entity.request.TrainingListRequest;
import com.example.training.core.entity.vo.FindTrainListVO;
import com.example.training.core.entity.vo.TrainingInfoVO;

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

    FindTrainListVO findTrainList(TrainingListRequest createTrainingRequest);

    void deleteTraining(SingleIdRequest singleIdRequest);

    TrainingInfoVO findTrainingInfo(SingleIdRequest singleIdRequest);
}
