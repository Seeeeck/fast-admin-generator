package pers.syq.fastadmin.generator.template;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collections;
import java.util.Map;

@Component
public class ServiceImplTemplate extends AbstractTemplate{
    @Override
    protected boolean toResources() {
        return false;
    }

    @Override
    protected String outputFilePath() {
        return "service" + File.separator + "impl";
    }

    @Override
    protected String templateFileName() {
        return "serviceImpl.java.vm";
    }

    @Override
    protected Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }
}
