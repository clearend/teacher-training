package com.example.training.core.service;

import com.example.training.core.entity.Material;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.training.core.entity.User;
import com.example.training.core.entity.request.MaterialListRequest;
import com.example.training.core.entity.request.SingleIdRequest;
import com.example.training.core.entity.request.UploadMaterialRequest;
import com.example.training.core.entity.vo.MaterialListVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
public interface IMaterialService extends IService<Material> {

    MaterialListVO findMaterialList(MaterialListRequest materialListRequest);

    void uploadMaterial(UploadMaterialRequest uploadMaterialRequest, User user);

    void deleteMaterial(SingleIdRequest singleIdRequest);
}
