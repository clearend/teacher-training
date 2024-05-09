package com.example.training.core.mapper;

import com.example.training.core.entity.Material;
import com.example.training.core.entity.dto.TrainingListDTO;
import com.example.training.core.entity.vo.MaterialListItemVO;
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
public interface MaterialMapper extends MPJBaseMapper<Material> {

    List<MaterialListItemVO> findMaterialList(@Param("param") TrainingListDTO trainingListDTO, @Param("auditStatus") Integer auditStatus );
}
