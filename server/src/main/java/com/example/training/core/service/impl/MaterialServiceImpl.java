package com.example.training.core.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.training.common.exceptions.BizException;
import com.example.training.core.entity.Material;
import com.example.training.core.entity.MaterialAudit;
import com.example.training.core.entity.SysFile;
import com.example.training.core.entity.User;
import com.example.training.core.entity.dto.TrainingListDTO;
import com.example.training.core.entity.enums.AuditEnum;
import com.example.training.core.entity.enums.FileStatusEnum;
import com.example.training.core.entity.enums.MaterialTypeEnum;
import com.example.training.core.entity.enums.RoleEnum;
import com.example.training.core.entity.request.MaterialListRequest;
import com.example.training.core.entity.request.SingleIdRequest;
import com.example.training.core.entity.request.UploadMaterialRequest;
import com.example.training.core.entity.vo.MaterialListItemVO;
import com.example.training.core.entity.vo.MaterialListVO;
import com.example.training.core.mapper.MaterialAuditMapper;
import com.example.training.core.mapper.MaterialMapper;
import com.example.training.core.service.IMaterialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    private MaterialMapper materialMapper;

    @Resource
    private MaterialAuditMapper materialAuditMapper;
    @Override
    public MaterialListVO findMaterialList(MaterialListRequest materialListRequest) {
        Integer currentPage = materialListRequest.getPageRequest().getCurrentPage();
        Integer pageSize = materialListRequest.getPageRequest().getPageSize();
        Integer offset = (currentPage - 1) * pageSize;

        MaterialListVO materialListVO = new MaterialListVO();

        MPJLambdaWrapper<Material> wrapper = new MPJLambdaWrapper<>();
        wrapper.leftJoin(User.class, User::getUserId, Material::getUserId)
                .leftJoin(SysFile.class, SysFile::getFileId, Material::getFileId)
                .eq(Material::getFileStatus, FileStatusEnum.PUBLISHED.getCode());
        materialListVO.setCount(materialMapper.selectCount(wrapper));

        wrapper.select(Material::getMaterialId, Material::getMaterialName, Material::getMaterialType, Material::getFileStatus)
                .select(User::getUserId, User::getUserName)
                .select(SysFile::getFileId, SysFile::getFileName, SysFile::getFileUrl)
                .selectAs(Material::getCreateTime, MaterialListItemVO::getUploadTime)
                .orderByDesc(Material::getCreateTime)
                .last("limit " + offset + ", " + pageSize);

        List<MaterialListItemVO> materialListItemVOList = materialMapper.selectJoinList(MaterialListItemVO.class, wrapper);
        if (Objects.nonNull(materialListItemVOList) && !materialListItemVOList.isEmpty()) {
            materialListVO.setMaterialList(materialListItemVOList);
        } else {
            materialListVO.setMaterialList(Collections.emptyList());
        }

        return materialListVO;

//        TrainingListDTO trainingListDTO = new TrainingListDTO();
//        trainingListDTO.setOffset(offset);
//        trainingListDTO.setLimit(pageSize);
//        List<MaterialListItemVO> materialListItemVOS =materialMapper.findMaterialList(trainingListDTO, AuditEnum.PASS.getCode());
//        return materialListItemVOS;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void uploadMaterial(UploadMaterialRequest uploadMaterialRequest, User user) {
        Material material = new Material();
        material.setMaterialId("m-" + IdUtil.fastSimpleUUID());
        material.setMaterialName(uploadMaterialRequest.getMaterialName());
        material.setMaterialType(MaterialTypeEnum.getByCode(uploadMaterialRequest.getMaterialType()));
        material.setFileId(uploadMaterialRequest.getFileId());
        material.setUserId(user.getUserId());

        if (user.getRole() == RoleEnum.USER) {
            material.setFileStatus(FileStatusEnum.AUDITING);
            materialMapper.insert(material);

            MaterialAudit materialAudit = new MaterialAudit();
            materialAudit.setMaterialAuditId("ma-" + IdUtil.fastSimpleUUID());
            materialAudit.setMaterialId(material.getMaterialId());
            materialAudit.setAuditStatus(AuditEnum.WAIT);
            materialAuditMapper.insert(materialAudit);
        } else {
            material.setFileStatus(FileStatusEnum.PUBLISHED);
            materialMapper.insert(material);
        }
    }

    @Override
    public void deleteMaterial(SingleIdRequest singleIdRequest) {
        Material material = materialMapper.selectOne(
                new LambdaQueryWrapper<Material>()
                        .select(Material::getId)
                        .eq(Material::getMaterialId, singleIdRequest.getId())
        );

        if (Objects.isNull(material)) {
            throw new BizException("待删除素材不存在");
        }

        materialMapper.deleteById(material);
    }
}
