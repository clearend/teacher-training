package com.example.training.core.service;

import com.example.training.core.entity.Material;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.training.core.entity.request.MaterialListRequest;
import com.example.training.core.entity.vo.MaterialListVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
public interface IMaterialService extends IService<Material> {

    List<MaterialListVO> findMaterialList(MaterialListRequest materialListRequest);
}
