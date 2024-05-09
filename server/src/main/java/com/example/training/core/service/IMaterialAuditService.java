package com.example.training.core.service;

import com.example.training.core.entity.MaterialAudit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.training.core.entity.request.MaterialAuditListRequest;
import com.example.training.core.entity.request.MaterialAuditRequest;
import com.example.training.core.entity.vo.MaterialAuditListVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
public interface IMaterialAuditService extends IService<MaterialAudit> {

    MaterialAuditListVO getMaterialAuditList(MaterialAuditListRequest materialAuditListRequest);

    void auditMaterial(MaterialAuditRequest materialAuditRequest);
}
