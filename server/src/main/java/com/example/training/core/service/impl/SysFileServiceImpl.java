package com.example.training.core.service.impl;

import cn.hutool.core.util.IdUtil;
import com.example.training.common.ResultResponse;
import com.example.training.common.enums.StatusEnum;
import com.example.training.core.entity.SysFile;
import com.example.training.core.entity.vo.SysFileVO;
import com.example.training.core.mapper.SysFileMapper;
import com.example.training.core.service.ISysFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService {

    Logger logger = LoggerFactory.getLogger(SysFileServiceImpl.class);

    @Resource
    private SysFileMapper sysFileMapper;

    private static final String DOMAIN = "http://114.116.252.42/server/files/";

    private static final String basePath = "/root/files/";

    @Override
    public ResultResponse<SysFileVO> upload(MultipartFile file) {
//        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
//        String basePath = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath() + "/src/main/resources/static/files/";
        String originalFileName = file.getOriginalFilename();
        String fileName = IdUtil.fastSimpleUUID() + "-" + file.getOriginalFilename();

        File uploadFile = new File(basePath + fileName);
        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            logger.error("上传失败", e);
            return ResultResponse.error(StatusEnum.SERVICE_ERROR, "文件上传失败");
        }

        SysFile sysFile = new SysFile();
        sysFile.setFileId("f-" + IdUtil.fastSimpleUUID());
        sysFile.setFileName(originalFileName);
        sysFile.setFileType(originalFileName.substring(originalFileName.lastIndexOf(".")));
        sysFile.setFilePath(fileName);
        sysFile.setFileUrl(DOMAIN + fileName);
        sysFileMapper.insert(sysFile);

        SysFileVO sysFileVO = new SysFileVO();
        BeanUtils.copyProperties(sysFile, sysFileVO);

        return ResultResponse.success(sysFileVO);
    }
}
