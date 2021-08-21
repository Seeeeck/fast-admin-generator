package pers.syq.fastadmin.generator.template;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.velocity.VelocityContext;
import pers.syq.fastadmin.generator.enums.ErrorCode;
import pers.syq.fastadmin.generator.exception.FastAdminException;
import pers.syq.fastadmin.generator.module.GeneratorData;
import pers.syq.fastadmin.generator.module.GlobalConfigEntity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public interface Template {


    /**
     * Whether the file is in the resources directory
     *
     * @return
     */
    boolean toResources();

    /**
     * Where to put the file
     * Enter the path after the module directory or resources directory
     * E.g: if the file is in the "java/com/example/project/moduleName/controller",just enter "controller".
     * if the file is in the "classpath:resources/static/js/" ,enter "static/js".
     *
     * @return
     */
    String outputFilePath();

    /**
     * The file name of the file placed in the "classpath:resources/template/"
     *
     * @return
     */
    String templateFileName();

    /**
     * The map will be put into the VelocityContext.And you can use properties of the map in the velocity template.
     *
     * @return
     */
    Map<String, Object> addExtraObjectMap();

    void generateCode(Map<String, Object> objectMap, ZipOutputStream zip);

    String ConcatOutputFilePath(String path);

    default VelocityContext createVelocityContext(Map<String, Object> objectMap) {
        objectMap.putAll(addExtraObjectMap());
        if (GeneratorData.globalConfig != null) {
            Map<String, Object> globalConfigMap = BeanUtil.beanToMap(GeneratorData.globalConfig);
            objectMap.putAll(globalConfigMap);
        }
        objectMap.put("datetime", DateUtil.today());
        return new VelocityContext(objectMap);
    }

    default void write2Zip(ZipOutputStream zip, StringWriter sw, String fileName) {
        try {
            zip.putNextEntry(new ZipEntry(fileName));
            IoUtil.write(zip, StandardCharsets.UTF_8, false, sw.toString());
        } catch (IOException e) {
            // TODO ex
            e.printStackTrace();
        } finally {
            IoUtil.close(sw);
            try {
                zip.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    default String absoluteOutputFilePath(String className) {
        String path = "main" + File.separator;
        if (toResources()) {
            path += "resources" + File.separator;
        } else {
            path = ConcatOutputFilePath(path);
        }
        return addFileName(path, className);
    }

    default String addFileName(String path, String className) {
        String outputFilePath = formatOutputFilePath();
        String fileName = getNoSuffixTemplateFileName();
        if (StrUtil.isEmpty(outputFilePath)) {
            return path + className + fileName;
        } else {
            return path + outputFilePath + File.separator + className + fileName;
        }
    }

    default String formatOutputFilePath() {
        String outputFilePath = outputFilePath();
        if (StrUtil.isEmpty(outputFilePath)) {
            return outputFilePath;
        }
        if (File.separator.equals(String.valueOf(outputFilePath.charAt(0)))) {
            outputFilePath = outputFilePath.substring(1);
        }
        if (File.separator.equals(String.valueOf(outputFilePath.charAt(outputFilePath.length() - 1)))) {
            outputFilePath = outputFilePath.substring(0, outputFilePath.length() - 1);
        }
        return outputFilePath;
    }

    default String getNoSuffixTemplateFileName() {
        return templateFileName().substring(0, templateFileName().lastIndexOf("."));
    }


    default String absoluteTemplateFilePath() {
        return "template" + File.separator + templateFileName();
    }
}
