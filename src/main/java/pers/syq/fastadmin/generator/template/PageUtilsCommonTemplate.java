package pers.syq.fastadmin.generator.template;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class PageUtilsCommonTemplate extends AbstractCommonTemplate {
    @Override
    public boolean toResources() {
        return false;
    }

    @Override
    public String outputFilePath() {
        return "utils";
    }

    @Override
    public String templateFileName() {
        return "PageUtils.java.vm";
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
