package com.example.training.core.mapper;

import com.example.training.core.entity.Material;
import com.example.training.core.entity.dto.TrainingListDTO;
import com.example.training.core.entity.vo.MaterialListVO;
import com.github.yulichang.base.MPJBaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
public interface MaterialMapper extends MPJBaseMapper<Material> {

    List<MaterialListVO> findMaterialList(TrainingListDTO trainingListDTO);
}
