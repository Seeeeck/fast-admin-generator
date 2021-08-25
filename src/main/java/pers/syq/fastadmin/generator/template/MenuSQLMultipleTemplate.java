package pers.syq.fastadmin.generator.template;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class MenuSQLMultipleTemplate extends AbstractMultipleTemplate{
    @Override
    public boolean toResources() {
        return true;
    }

    @Override
    public String outputFilePath() {
        return "sql";
    }

    @Override
    public String templateFileName() {
        return "Menu.sql.vm";
    }

    @Override
    public Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }
}
