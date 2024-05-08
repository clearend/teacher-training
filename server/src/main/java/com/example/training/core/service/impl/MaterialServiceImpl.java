package com.example.training.core.service.impl;

import com.example.training.core.entity.Material;
import com.example.training.core.entity.dto.TrainingListDTO;
import com.example.training.core.entity.request.MaterialListRequest;
import com.example.training.core.entity.vo.MaterialListVO;
import com.example.training.core.mapper.MaterialMapper;
import com.example.training.core.mapper.TrainingAuditMapper;
import com.example.training.core.service.IMaterialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements IMaterialService {
    @Resource
    private MaterialMapper  materialMapper;
    @Override
    public List<MaterialListVO> findMaterialList(MaterialListRequest materialListRequest) {
        Integer currentPage = materialListRequest.getPageRequest().getCurrentPage();
        Integer pageSize = materialListRequest.getPageRequest().getPageSize();
        Integer offset = (currentPage - 1) * pageSize;
        TrainingListDTO trainingListDTO = new TrainingListDTO();
        trainingListDTO.setOffset(offset);
        trainingListDTO.setLimit(pageSize);
        List<MaterialListVO> materialListVOS=materialMapper.findMaterialList(trainingListDTO);
        return materialListVOS;
    }
}
