package com.example.training.core.service;

import com.example.training.common.ResultResponse;
import com.example.training.core.entity.SysFile;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
public interface ISysFileService extends IService<SysFile> {

    ResultResponse<?> upload(MultipartFile file);
}
