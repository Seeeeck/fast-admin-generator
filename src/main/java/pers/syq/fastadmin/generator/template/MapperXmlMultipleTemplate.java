package pers.syq.fastadmin.generator.template;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class MapperXmlMultipleTemplate extends AbstractMultipleTemplate {

    @Override
    public boolean toResources() {
        return true;
    }

    @Override
    public String outputFilePath() {
        return "mapper";
    }

    @Override
    public String templateFileName() {
        return "Mapper.xml.vm";
    }

    @Override
    public Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }
}
