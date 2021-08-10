package pers.syq.fastadmin.generator.template;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class ServiceTemplate extends AbstractTemplate{

    @Override
    protected boolean toResources() {
        return false;
    }

    @Override
    protected String outputFilePath() {
        return "service";
    }

    @Override
    protected String templateFileName() {
        return "Service.java.vm";
    }

    @Override
    protected Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }
}
