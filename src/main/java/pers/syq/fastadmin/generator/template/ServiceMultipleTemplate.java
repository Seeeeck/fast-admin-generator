package pers.syq.fastadmin.generator.template;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class ServiceMultipleTemplate extends AbstractMultipleTemplate {

    @Override
    public boolean toResources() {
        return false;
    }

    @Override
    public String outputFilePath() {
        return "service";
    }

    @Override
    public String templateFileName() {
        return "Service.java.vm";
    }

    @Override
    public Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }
}
