package pers.syq.fastadmin.generator.template;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class ControllerTemplate extends AbstractTemplate {

    @Override
    protected boolean toResources() {
        return false;
    }

    @Override
    protected String outputFilePath() {
        return "controller";
    }

    @Override
    protected String templateFileName() {
        return "Controller.java.vm";
    }

    @Override
    protected Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }
}
