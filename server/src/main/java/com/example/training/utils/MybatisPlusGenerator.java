package com.example.training.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;


public class MybatisPlusGenerator {
    private static String url = "jdbc:mysql://114.116.252.42:3306/teacher_training?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";

    private static String username = "root";

    private static String password = "3ADFKdPY@8G!cs@D";

    public static void main(String[] args) {
        System.out.println(url);
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("coder") // 设置作者
                            .enableSpringdoc()
                            .outputDir("/workspace/Projects/teacher-training/server/src/main/java");
                })
                .dataSourceConfig(builder ->
                    builder.typeConvertHandler(((globalConfig, typeRegistry, metaInfo) -> {
                        int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                        if (typeCode == Types.SMALLINT) {
                            return DbColumnType.INTEGER;
                        }
                        return typeRegistry.getColumnType(metaInfo);
                    })))
                .packageConfig(builder -> {
                    builder.parent("com.example.training")
                            .moduleName("core")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "/workspace/Projects/teacher-training/server/src/main/resources/mappers"));
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
