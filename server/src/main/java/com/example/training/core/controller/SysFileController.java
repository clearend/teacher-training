package com.example.training.core.controller;

import com.example.training.common.ResultResponse;
import com.example.training.common.exceptions.BizException;
import com.example.training.core.service.ISysFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
@RestController
@CrossOrigin
@ControllerAdvice
@Tag(name = "文件控制器")
@RequestMapping("/core/sysFile")
public class SysFileController {

    @Resource
    private ISysFileService sysFileService;

    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    public ResultResponse<?> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new BizException("文件为空");
        }
        return sysFileService.upload(file);
    }

}
