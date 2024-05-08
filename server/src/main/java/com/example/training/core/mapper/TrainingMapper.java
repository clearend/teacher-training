package com.example.training.core.mapper;

import com.example.training.core.entity.Training;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.training.core.entity.dto.TrainingListDTO;
import com.example.training.core.entity.request.CreateTrainingRequest;
import com.example.training.core.entity.vo.FindTrainListVO;
import com.example.training.core.entity.vo.TrainingListItemVO;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
public interface TrainingMapper extends MPJBaseMapper<Training> {

    void createTraining(CreateTrainingRequest createTrainingRequest);

    List<TrainingListItemVO> findTrainList(@Param("param") TrainingListDTO trainingListDTO);

    Float getComp(FindTrainListVO vo);
}
