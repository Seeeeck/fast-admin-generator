package pers.syq.fastadmin.generator.template;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
@Component
public class PageUtilsCommonTemplate extends AbstractCommonTemplate{
    @Override
    protected boolean toResources() {
        return false;
    }

    @Override
    protected String outputFilePath() {
        return "utils";
    }

    @Override
    protected String templateFileName() {
        return "PageUtils.java.vm";
    }

    @Override
    protected Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }
}
