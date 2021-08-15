package pers.syq.fastadmin.generator.template;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collections;
import java.util.Map;

@Component
public class ServiceImplMultipleTemplate extends AbstractMultipleTemplate {
    @Override
    public boolean toResources() {
        return false;
    }

    @Override
    public String outputFilePath() {
        return "service" + File.separator + "impl";
    }

    @Override
    public String templateFileName() {
        return "serviceImpl.java.vm";
    }

    @Override
    public Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }
}
