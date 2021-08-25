package pers.syq.fastadmin.generator.template;

import org.springframework.stereotype.Component;
import pers.syq.fastadmin.generator.module.GeneratorData;

import java.io.File;
import java.util.Collections;
import java.util.Map;
@Component
public class SaveOrUpdateMultipleTemplate extends AbstractVueMultipleTemplate{
    @Override
    public String outputFilePath() {
        return "views" + File.separator + GeneratorData.globalConfig.getModuleName();
    }

    @Override
    public String templateFileName() {
        return "-save-or-update.vue.vm";
    }

    @Override
    public Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }

    @Override
    public String addFileName(String path, String className) {
        String outputFilePath = formatOutputFilePath();
        String fileName = getNoSuffixTemplateFileName();
        return path + outputFilePath + File.separator + className + File.separator + className + fileName;
    }
}
