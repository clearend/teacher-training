package com.example.training.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.training.common.exceptions.BizException;
import com.example.training.core.entity.Material;
import com.example.training.core.entity.MaterialAudit;
import com.example.training.core.entity.SysFile;
import com.example.training.core.entity.User;
import com.example.training.core.entity.enums.AuditEnum;
import com.example.training.core.entity.enums.FileStatusEnum;
import com.example.training.core.entity.enums.RoleEnum;
import com.example.training.core.entity.request.MaterialAuditListRequest;
import com.example.training.core.entity.request.MaterialAuditRequest;
import com.example.training.core.entity.vo.MaterialAuditListItemVO;
import com.example.training.core.entity.vo.MaterialAuditListVO;
import com.example.training.core.entity.vo.MaterialListVO;
import com.example.training.core.mapper.MaterialAuditMapper;
import com.example.training.core.mapper.MaterialMapper;
import com.example.training.core.service.IMaterialAuditService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
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
public class MaterialAuditServiceImpl extends ServiceImpl<MaterialAuditMapper, MaterialAudit> implements IMaterialAuditService {

    @Resource
    private MaterialAuditMapper materialAuditMapper;

    @Resource
    private MaterialMapper materialMapper;

    @Override
    public MaterialAuditListVO getMaterialAuditList(MaterialAuditListRequest materialAuditListRequest, User user) {
        Integer currentPage = materialAuditListRequest.getPageRequest().getCurrentPage();
        Integer pageSize = materialAuditListRequest.getPageRequest().getPageSize();
        Integer offset = (currentPage - 1) * pageSize;

        MaterialAuditListVO materialListVO = new MaterialAuditListVO();

        MPJLambdaWrapper<MaterialAudit> wrapper = new MPJLambdaWrapper<>();
        wrapper.leftJoin(Material.class, Material::getMaterialId, MaterialAudit::getMaterialId)
                .leftJoin(User.class, User::getUserId, Material::getUserId)
                .leftJoin(SysFile.class, SysFile::getFileId, Material::getFileId);

        if (user.getRole() == RoleEnum.USER) {
            wrapper.eq(MaterialAudit::getAuditUserId, user.getUserId());
        }

        materialListVO.setCount(materialAuditMapper.selectCount(wrapper));

        wrapper.select(MaterialAudit::getMaterialAuditId, MaterialAudit::getAuditStatus, MaterialAudit::getAuditTime, MaterialAudit::getAuditRemark)
                .select(Material::getMaterialId, Material::getMaterialName, Material::getMaterialType)
                .select(User::getUserId, User::getUserName)
                .select(SysFile::getFileId, SysFile::getFileName, SysFile::getFileUrl)
                .selectAs(Material::getCreateTime, MaterialAuditListItemVO::getUploadTime)
                .orderByAsc(MaterialAudit::getAuditStatus)
                .orderByDesc(Material::getCreateTime)
                .last("limit " + offset + ", " + pageSize);

        List<MaterialAuditListItemVO> materialAuditList = materialAuditMapper.selectJoinList(MaterialAuditListItemVO.class, wrapper);
        if (Objects.nonNull(materialAuditList) && !materialAuditList.isEmpty()) {
            materialListVO.setMaterialAuditList(materialAuditList);
        } else {
            materialListVO.setMaterialAuditList(Collections.emptyList());
        }

        return materialListVO;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void auditMaterial(MaterialAuditRequest materialAuditRequest) {
        MaterialAudit materialAudit = materialAuditMapper.selectOne(
                new LambdaQueryWrapper<MaterialAudit>()
                        .select(MaterialAudit::getId, MaterialAudit::getMaterialId)
                        .eq(MaterialAudit::getMaterialAuditId, materialAuditRequest.getMaterialAuditId())
        );

        if (materialAudit == null) {
            throw new BizException("待审批素材不存在");
        }

        materialAudit.setAuditRemark(materialAuditRequest.getAuditRemark());
        materialAudit.setAuditStatus(materialAuditRequest.getAuditResult() == 0 ? AuditEnum.REJECT : AuditEnum.PASS);
        materialAudit.setAuditTime(new Date());
        materialAuditMapper.updateById(materialAudit);

        Material material = materialMapper.selectOne(
                new LambdaQueryWrapper<Material>()
                        .select(Material::getId)
                        .eq(Material::getMaterialId, materialAudit.getMaterialId())
        );
        material.setFileStatus(materialAuditRequest.getAuditResult() == 0 ? FileStatusEnum.REJECTED : FileStatusEnum.PUBLISHED);
        materialMapper.updateById(material);
    }
}
