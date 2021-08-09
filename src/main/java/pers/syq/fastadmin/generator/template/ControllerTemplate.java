package pers.syq.fastadmin.generator.template;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class ControllerTemplate extends AbstractTemplate {
    @Override
    boolean toResources() {
        return false;
    }

    @Override
    String outputFilePath() {
        return "controller";
    }

    @Override
    String templateFileName() {
        return "Controller.java.vm";
    }

    @Override
    Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }
}
