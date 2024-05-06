package com.example.training.core.service.impl;

import com.example.training.core.entity.Material;
import com.example.training.core.mapper.MaterialMapper;
import com.example.training.core.service.IMaterialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
