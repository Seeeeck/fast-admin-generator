package pers.syq.fastadmin.generator.template;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
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

public abstract class AbstractTemplate {
    abstract boolean toResources();

    abstract String outputFilePath();

    abstract String templateFileName();

    abstract Map<String, Object> addExtraObjectMap();

    public void generateCode(Map<String,Object> objectMap,ZipOutputStream zip){
        VelocityContext velocityContext = createVelocityContext(objectMap);
        Object className = velocityContext.get("className");
        if (className == null){
            throw new FastAdminException("Unknown error", HttpStatus.HTTP_INTERNAL_ERROR);
        }
        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate(absoluteTemplateFilePath(), "UTF-8");
        tpl.merge(velocityContext, sw);
        String fileName = absoluteOutputFilePath((String) className);
        write2Zip(zip,sw,fileName);
    }

    private void write2Zip(ZipOutputStream zip,StringWriter sw,String fileName){
        try {
            zip.putNextEntry(new ZipEntry(fileName));
            IoUtil.write(zip, StandardCharsets.UTF_8,false,sw.toString());
        } catch (IOException e) {
            // TODO ex
            e.printStackTrace();
        }finally {
            IoUtil.close(sw);
            try {
                zip.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String absoluteOutputFilePath(String className){
        if (GeneratorData.globalConfig == null){
            throw new FastAdminException(ErrorCode.NULL_GLOBAL_CONFIG);
        }
        GlobalConfigEntity globalConfig = GeneratorData.globalConfig;
        String path = "main" + File.separator;
        if (toResources()){
            path += "resources" + File.separator;
        }else {
            path += "java" + File.separator + globalConfig.getPkg().replace(".",File.separator) +
                    File.separator + globalConfig.getModuleName() + File.separator;
        }
        return addFileName(path,className);
    }

    private String addFileName(String path,String className){
        String outputFilePath = formatOutputFilePath();
        String javaFileName = getNoSuffixTemplateFileName();
        if (StrUtil.isEmpty(outputFilePath)){
            return path + className + javaFileName;
        }else {
            return path + outputFilePath + File.separator + className + javaFileName;
        }
    }

    private String formatOutputFilePath(){
        String outputFilePath = outputFilePath();
        if (StrUtil.isEmpty(outputFilePath)){
            return outputFilePath;
        }
        if (File.separator.equals(String.valueOf(outputFilePath.charAt(0)))){
            outputFilePath = outputFilePath.substring(1);
        }
        if (File.separator.equals(String.valueOf(outputFilePath.charAt(outputFilePath.length()-1)))){
            outputFilePath = outputFilePath.substring(0,outputFilePath.length()-1);
        }
        return outputFilePath;
    }

    private String getNoSuffixTemplateFileName(){
        return templateFileName().substring(0,templateFileName().lastIndexOf("."));
    }

    private VelocityContext createVelocityContext(Map<String,Object> objectMap){
        objectMap.putAll(addExtraObjectMap());
        if (GeneratorData.globalConfig != null) {
            Map<String, Object> globalConfigMap = BeanUtil.beanToMap(GeneratorData.globalConfig);
            objectMap.putAll(globalConfigMap);
        }
        objectMap.put("datetime", DateUtil.today());
        return new VelocityContext(objectMap);
    }

    private String absoluteTemplateFilePath(){
        return "template" + File.separator + templateFileName();
    }
}
