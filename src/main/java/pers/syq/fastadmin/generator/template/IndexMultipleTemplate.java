package pers.syq.fastadmin.generator.template;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;
import pers.syq.fastadmin.generator.module.GeneratorData;

import java.io.File;
import java.util.Collections;
import java.util.Map;
@Component
public class IndexMultipleTemplate extends AbstractVueMultipleTemplate{
    @Override
    public String outputFilePath() {
        return "views" + File.separator + GeneratorData.globalConfig.getModuleName();
    }

    @Override
    public String templateFileName() {
        return "index.vue.vm";
    }

    @Override
    public Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }


    @Override
    public String addFileName(String path, String className) {
        String outputFilePath = formatOutputFilePath();
        String fileName = getNoSuffixTemplateFileName();
        return path + outputFilePath + File.separator + className + File.separator + fileName;
    }
}
