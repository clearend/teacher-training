package com.example.training.core.mapper;

import com.example.training.core.entity.TrainingUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.training.core.entity.User;
import com.example.training.core.entity.request.AddPersonRequest;
import com.github.yulichang.base.MPJBaseMapper;
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
public interface TrainingUserMapper extends MPJBaseMapper<TrainingUser> {

    void addPerson(@Param("trainingId") String trainingId,@Param("user") User user);

    void cancelSignUp( @Param("trainingId") String trainingId, @Param("user") String user);
}
