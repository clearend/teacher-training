package com.example.training.core.service.impl;

import cn.hutool.core.util.IdUtil;
import com.example.training.core.entity.Training;
import com.example.training.core.entity.enums.TrainTypeEnum;
import com.example.training.core.entity.request.CreateTrainingRequest;
import com.example.training.core.entity.vo.FindTrainListVO;
import com.example.training.core.mapper.TrainingMapper;
import com.example.training.core.mapper.UserMapper;
import com.example.training.core.service.ITrainingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class TrainingServiceImpl extends ServiceImpl<TrainingMapper, Training> implements ITrainingService {
    @Resource
    private TrainingMapper trainingMapper;

    /**
     * 新建培训
     * @param createTrainingRequest
     */
    @Override
    public void createTraining(CreateTrainingRequest createTrainingRequest) {
        Training training = new Training();
        BeanUtils.copyProperties(createTrainingRequest, training);
        training.setTrainingId(IdUtil.fastSimpleUUID());
        training.setTrainingTime(new Date(createTrainingRequest.getTrainingTime()));
        training.setTrainingType(TrainTypeEnum.getByCode(createTrainingRequest.getTrainingType()));
        trainingMapper.insert(training);

//        String uuid= IdUtil.fastSimpleUUID();
//        createTrainingRequest.setTrainingId(uuid);
//        trainingMapper.createTraining(createTrainingRequest);
    }

    /**
     * 查询培训列表
     * @param createTrainingRequest
     * @return
     */
    @Override
    public List<FindTrainListVO> findTrainList(CreateTrainingRequest createTrainingRequest) {
        List<FindTrainListVO> list = new ArrayList<>();
        list=trainingMapper.findTrainList(createTrainingRequest);
        if(list!=null && list.size()>0){
            for(FindTrainListVO vo:list){
                //查询完成率
              Float comp= trainingMapper.getComp(vo);
              if(comp!=null){
                  String formattedComp = String.format("%.2f", comp)+"%";
                  vo.setComp(formattedComp);
              }
            }
        }
        return list;
    }
}
