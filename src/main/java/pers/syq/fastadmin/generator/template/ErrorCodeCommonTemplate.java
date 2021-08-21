package pers.syq.fastadmin.generator.template;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class ErrorCodeCommonTemplate extends AbstractCommonTemplate {
    @Override
    public boolean toResources() {
        return false;
    }

    @Override
    public String outputFilePath() {
        return "exception";
    }

    @Override
    public String templateFileName() {
        return "ErrorCode.java.vm";
    }

    @Override
    public Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }

    @Override
    public boolean isSecurityComponent() {
        return false;
    }
}
