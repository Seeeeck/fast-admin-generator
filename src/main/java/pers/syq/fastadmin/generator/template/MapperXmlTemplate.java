package pers.syq.fastadmin.generator.template;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class MapperXmlTemplate extends AbstractTemplate{

    @Override
    protected boolean toResources() {
        return true;
    }

    @Override
    protected String outputFilePath() {
        return "mapper";
    }

    @Override
    protected String templateFileName() {
        return "Mapper.xml.vm";
    }

    @Override
    protected Map<String, Object> addExtraObjectMap() {
        return Collections.emptyMap();
    }
}
