package pers.syq.fastadmin.generator.template;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collections;
import java.util.Map;

@Component
public class ServiceImplTemplate extends AbstractTemplate{
    @Override
    boolean toResources() {
        return false;
    }

    @Override
    String outputFilePath() {
        return "service" + File.separator + "impl";
    }

    @Override
    String templateFileName() {
        return "serviceImpl.java.vm";
    }

    @Override
    Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }
}
