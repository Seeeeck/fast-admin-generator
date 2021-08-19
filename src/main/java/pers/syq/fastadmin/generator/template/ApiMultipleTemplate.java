package pers.syq.fastadmin.generator.template;

import org.springframework.stereotype.Component;
import pers.syq.fastadmin.generator.module.GeneratorData;

import java.util.Collections;
import java.util.Map;

@Component
public class ApiMultipleTemplate extends AbstractVueMultipleTemplate {

    @Override
    public String outputFilePath() {
        return "api/"+ GeneratorData.globalConfig.getModuleName();
    }

    @Override
    public String templateFileName() {
        return ".js.vm";
    }

    @Override
    public Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }
}
