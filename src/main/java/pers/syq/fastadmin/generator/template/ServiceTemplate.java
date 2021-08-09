package pers.syq.fastadmin.generator.template;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class ServiceTemplate extends AbstractTemplate{
    @Override
    boolean toResources() {
        return false;
    }

    @Override
    String outputFilePath() {
        return "service";
    }

    @Override
    String templateFileName() {
        return "Service.java.vm";
    }

    @Override
    Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }
}
