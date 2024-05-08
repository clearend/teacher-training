package com.example.training.core.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.training.common.exceptions.BizException;
import com.example.training.core.entity.Training;
import com.example.training.core.entity.TrainingUser;
import com.example.training.core.entity.User;
import com.example.training.core.entity.dto.TrainingListDTO;
import com.example.training.core.entity.enums.TrainTypeEnum;
import com.example.training.core.entity.request.CreateTrainingRequest;
import com.example.training.core.entity.request.SingleIdRequest;
import com.example.training.core.entity.request.TrainingListRequest;
import com.example.training.core.entity.vo.FindTrainListVO;
import com.example.training.core.entity.vo.TrainingInfoVO;
import com.example.training.core.entity.vo.TrainingListItemVO;
import com.example.training.core.entity.vo.TrainingUserListItemVO;
import com.example.training.core.mapper.TrainingMapper;
import com.example.training.core.mapper.TrainingUserMapper;
import com.example.training.core.mapper.UserMapper;
import com.example.training.core.service.ITrainingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Resource
    private TrainingUserMapper trainingUserMapper;

    /**
     * 新建培训
     * @param createTrainingRequest
     */
    @Override
    public void createTraining(CreateTrainingRequest createTrainingRequest) {
        Training training = new Training();
        BeanUtils.copyProperties(createTrainingRequest, training);
        training.setTrainingId("t-" + IdUtil.fastSimpleUUID());
        training.setTrainingTime(new Date(createTrainingRequest.getTrainingTime()));
        training.setTrainingType(TrainTypeEnum.getByCode(createTrainingRequest.getTrainingType()));
        trainingMapper.insert(training);

//        String uuid= IdUtil.fastSimpleUUID();
//        createTrainingRequest.setTrainingId(uuid);
//        trainingMapper.createTraining(createTrainingRequest);
    }

    @Override
    public FindTrainListVO findTrainList(TrainingListRequest trainingListRequest) {
        Integer currentPage = trainingListRequest.getPageRequest().getCurrentPage();
        Integer pageSize = trainingListRequest.getPageRequest().getPageSize();
        Integer offset = (currentPage - 1) * pageSize;

        LambdaQueryWrapper<Training> countWrapper = new LambdaQueryWrapper<>();
        Long count = trainingMapper.selectCount(countWrapper);

        TrainingListDTO trainingListDTO = new TrainingListDTO();
        trainingListDTO.setOffset(offset);
        trainingListDTO.setLimit(pageSize);

        FindTrainListVO findTrainListVO = new FindTrainListVO();
        List<TrainingListItemVO> trainingListItemVOList = trainingMapper.findTrainList(trainingListDTO);
        findTrainListVO.setCount(count);
        if (trainingListItemVOList == null || trainingListItemVOList.isEmpty()) {
            findTrainListVO.setTrainingList(new ArrayList<>());
        } else {
            findTrainListVO.setTrainingList(trainingListItemVOList);
        }

//        if(list!=null && list.size()>0){
//            for(FindTrainListVO vo:list){
//                //查询完成率
//              Float comp= trainingMapper.getComp(vo);
//              if(comp!=null){
//                  String formattedComp = String.format("%.2f", comp)+"%";
//                  vo.setComp(formattedComp);
//              }
//            }
//        }
        return findTrainListVO;
    }

    @Override
    public void deleteTraining(SingleIdRequest singleIdRequest) {
        Training training = trainingMapper.selectOne(new LambdaQueryWrapper<Training>().eq(Training::getTrainingId, singleIdRequest.getId()));

        if (Objects.isNull(training)) {
            throw new BizException("待删除培训不存在");
        }

        trainingMapper.deleteById(training.getId());
    }

    @Override
    public TrainingInfoVO findTrainingInfo(SingleIdRequest singleIdRequest) {
        Training training = trainingMapper.selectOne(new LambdaQueryWrapper<Training>().eq(Training::getTrainingId, singleIdRequest.getId()));
        if (Objects.isNull(training)) {
            throw new BizException("待查询培训不存在");
        }

        MPJLambdaWrapper<TrainingUser> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(User::getUserId, User::getUserName, User::getPhone, User::getEmail, User::getJobId, User::getGender)
                .select(TrainingUser::getStatus)
                .leftJoin(User.class, User::getUserId, TrainingUser::getUserId)
                .eq(TrainingUser::getTrainingId, singleIdRequest.getId());

        List<TrainingUserListItemVO> trainingUserListItemVOList = trainingUserMapper.selectJoinList(TrainingUserListItemVO.class, wrapper);

        TrainingInfoVO trainingInfoVO = new TrainingInfoVO();
        BeanUtils.copyProperties(training, trainingInfoVO);
        if (trainingUserListItemVOList != null && !trainingUserListItemVOList.isEmpty()) {
            trainingInfoVO.setTrainingUserList(trainingUserListItemVOList);
        } else {
            trainingInfoVO.setTrainingUserList(Collections.emptyList());
        }

        return trainingInfoVO;
    }
}
