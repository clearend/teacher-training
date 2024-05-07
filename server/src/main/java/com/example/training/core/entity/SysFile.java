package com.example.training.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author coder
 * @since 2024-05-06
 */
@Setter
@Getter
@TableName("sys_file")
@Schema(name = "SysFile", description = "")
public class SysFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "文件id")
    private String fileId;

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "存储路径")
    private String filePath;

    @Schema(description = "文件类型")
    private Integer fileType;

    @Schema(description = "文件url")
    private String fileUrl;

    @Schema(description = "是否删除")
    @TableLogic
    private Integer isDel;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Override
    public String toString() {
        return "SysFile{" +
            "id = " + id +
            ", fileId = " + fileId +
            ", fileName = " + fileName +
            ", filePath = " + filePath +
            ", fileType = " + fileType +
            ", fileUrl = " + fileUrl +
            ", isDel = " + isDel +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
        "}";
    }
}
